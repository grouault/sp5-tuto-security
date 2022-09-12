package fr.exagone.dao;

import fr.exagone.entities.AppRole;
import fr.exagone.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, Long> {

    public AppRole findByRoleName(String roleName);

}
