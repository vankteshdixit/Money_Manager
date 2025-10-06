package vanktesh.example.Money_Manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vanktesh.example.Money_Manager.dto.CategoryDTO;
import vanktesh.example.Money_Manager.entity.CategoryEntity;
import vanktesh.example.Money_Manager.entity.ProfileEntity;
import vanktesh.example.Money_Manager.repository.CategoryRepository;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;

//    Save category

    public CategoryDTO saveCategory(CategoryDTO categoryDTO){
        ProfileEntity profile = profileService.getCurrentProfile();
//        System.out.println("efedccdcdcdcd");
        if(categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())){
           throw new RuntimeException("Category with this name already exist");
        }
//        System.out.println("efrf");
        CategoryEntity newCategory = toEntity(categoryDTO, profile);
//        System.out.println("wfsdcx");
        newCategory = categoryRepository.save(newCategory);
//        System.out.println("jkjyg");
        return toDTO(newCategory);
    }

//    helper methods
    private CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity profile){
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .icon(categoryDTO.getIcon())
                .profile(profile)
                .type(categoryDTO.getType())
                .build();
    }

    private CategoryDTO toDTO(CategoryEntity entity) {
        return CategoryDTO.builder()
                .id(entity.getId())
                .profileId(entity.getProfile() != null ? entity.getProfile().getId(): null)
                .name(entity.getName())
                .icon(entity.getIcon())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .type(entity.getType())
                .build();
    }
}