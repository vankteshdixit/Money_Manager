package vanktesh.example.Money_Manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vanktesh.example.Money_Manager.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>{

    //    "findByEmail" automatically invoke the SQL query
    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByActivationToken(String activationToken);
}

