package fr.exagone.service;

import fr.exagone.dao.UserRepository;
import fr.exagone.entities.AppRole;
import fr.exagone.entities.AppUser;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepository.findByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException(username);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().stream().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        return new User(user.getUserName(), user.getPassword(), authorities);
    }

}
