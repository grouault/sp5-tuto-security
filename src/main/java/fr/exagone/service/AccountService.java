package fr.exagone.service;

import fr.exagone.entities.AppRole;
import fr.exagone.entities.AppUser;

public interface AccountService {

    public AppUser saveUser(AppUser user);

    public AppRole saveRole(AppRole role);

    public void addRoleToUser(String username, String roleName);

    public AppUser findUserByUserName(String username);

}
