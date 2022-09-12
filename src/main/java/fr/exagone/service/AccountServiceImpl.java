package fr.exagone.service;

import fr.exagone.dao.RoleRepository;
import fr.exagone.dao.UserRepository;
import fr.exagone.entities.AppRole;
import fr.exagone.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser user) {
        String hashPwd = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPwd);
        return userRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void addRoleToUser(String username, String roleName) {
        AppRole role = roleRepository.findByRoleName(roleName);
        AppUser user = userRepository.findByUserName(username);
        user.getRoles().add(role);
    }

    @Override
    public AppUser findUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

}
