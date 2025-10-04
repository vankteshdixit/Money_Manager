package vanktesh.example.Money_Manager.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import vanktesh.example.Money_Manager.dto.AuthDTO;
import vanktesh.example.Money_Manager.dto.ProfileDTO;
import vanktesh.example.Money_Manager.entity.ProfileEntity;
import vanktesh.example.Money_Manager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vanktesh.example.Money_Manager.util.JwtUtil;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ProfileDTO registerProfile(ProfileDTO profileDTO){
        ProfileEntity newProfile = toEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile = profileRepository.save(newProfile);
//        Send Activation email
        String activationLink = "http://localhost:8080/api/v1.0/activate?token=" + newProfile.getActivationToken();
        String subject = "Activate your Money Manager account";
        String body = "Click on the following link to activate your account: "+ activationLink;
        emailService.sendEmail(newProfile.getEmail(), subject, body);

        return toDTO(newProfile);
    }

    //    helper method to convert DTO to Entity
    public ProfileEntity toEntity(ProfileDTO profileDTO){
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(passwordEncoder.encode(profileDTO.getPassword()))
//                .password(profileDTO.getPassword())
                .profileImageUrl(profileDTO.getProfileImageUrl())
                .createdAt(profileDTO.getCreatedAt())
                .updatedAt(profileDTO.getUpdatedAt())
                .build();
    }

    public ProfileDTO toDTO(ProfileEntity profileEntity){
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }
    public boolean activateProfile(String activationToken){
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);//clear the activation toke
                    profileRepository.save(profile);
                    return true;
                })
                .orElse(false);
    }
    public boolean isAccountActive(String email){
        return profileRepository.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);
    }
    public ProfileEntity getCurrentProfile(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+ email));
    }
    public ProfileDTO getPublicProfile(String email){
        ProfileEntity currentUser = null;
        if(email == null) {
            currentUser = getCurrentProfile();
        }else{
            currentUser = profileRepository.findByEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("Profile not found for the email"));
        }
        return ProfileDTO.builder()
                .id(currentUser.getId())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .profileImageUrl(currentUser.getProfileImageUrl())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }
    public Map<String, Object> authenticateAndGenerateToken(AuthDTO authDTO){
        try{
            ProfileEntity p = profileRepository.findByEmail(authDTO.getEmail()).orElse(null);
            if (p == null) {
                throw new RuntimeException("No user with email: " + authDTO.getEmail());
            }
//            System.out.println("DB password (encoded): " + p.getPassword());
//            System.out.println("raw password: " + authDTO.getPassword());
//            System.out.println("matches? " + passwordEncoder.matches(authDTO.getPassword(), p.getPassword()));
//            System.out.println("isActive? " + p.getIsActive());

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
//            Generate the JWT token
            String token = jwtUtil.generateToken(authDTO.getEmail());
            return Map.of(
                    "token", token,
                    "user", getPublicProfile(authDTO.getEmail())
            );
        }catch (Exception e){
//            e.printStackTrace();
            throw new RuntimeException("Invalid email or password");
        }
    }
}