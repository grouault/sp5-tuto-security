package fr.exagone.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource datasource;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswdEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(datasource)
            // .withDefaultSchema()
            .passwordEncoder(getBCryptPasswdEncoder())
            .usersByUsernameQuery("select user_name as principal, password as credentials, 'true' as enabled from App_User where user_name = ?")
            .authoritiesByUsernameQuery("" +
                    "select appUser.user_name as principal, appRole.role_name as role " +
                    "from App_User_Roles as userRoles " +
                    " inner join App_User as appUser on appUser.id = userRoles.app_user_id " +
                    " inner join App_Role as appRole on appRole.id = userRoles.roles_id " +
                    "where appUser.user_name = ?")
            // pour pouvoir utiliser '.hasRole' plutot que 'hasAuthority'
            .rolePrefix("ROLE_")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.headers().frameOptions().disable();
        // desactivation csrf - active par défaut.
        http.csrf().disable();
        // toutes les ressources necessitent une authentification

        // autre condition pour les mehtodes POST
        // http.authorizeRequests().antMatchers(HttpMethod.POST).hasRole("ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.POST).hasRole("ADMIN");
        // http.authorizeRequests().antMatchers(HttpMethod.POST).hasAuthority("ADMIN");
            // .hasAuthority("ADMIN");
        // http.authorizeRequests().antMatchers("/save**/**","/delete**/**").hasRole("ADMIN");
        // toutes les requetes http necessite de passer par authentification

        // j'autorise tout
        // http.authorizeRequests().anyRequest().permitAll();

        // autorisation sur un path precis.
        //http.authorizeRequests().antMatchers("/user/**").permitAll();

        // toutes les autres necessitent une authentification
        http.authorizeRequests().anyRequest().authenticated();
    }

}
