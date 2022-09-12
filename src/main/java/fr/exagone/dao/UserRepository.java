package fr.exagone.dao;

import fr.exagone.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    public AppUser findByUserName(String username);

}
