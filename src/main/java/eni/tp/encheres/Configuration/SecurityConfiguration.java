
package eni.tp.encheres.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // Query spécifique pour adapter UserdetailsManager avec la BDD
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT pseudo, mot_de_passe, 1 AS enabled FROM UTILISATEURS WHERE pseudo=?");

        // QUERY pour vérifier si l'utilisateur qui se connecte est admin ou non
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT pseudo, CASE WHEN administrateur = 1 THEN 'ROLE_ADMIN' ELSE 'ROLE_USER' END AS authority " +
                        "FROM UTILISATEURS WHERE pseudo=?");


        // Pour faire l'inscription (possiblement inutile)
        jdbcUserDetailsManager.setCreateUserSql("INSERT INTO UTILISATEURS " +
                "(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0)");

        return jdbcUserDetailsManager;
    }

    /**
     * Restriction des URLs selon la connexion utilisateur et leurs rôles
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers(HttpMethod.GET, "/modifier-profil").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/nouvelle-vente").permitAll();

                    //Permettre à tous les utilisateurs d'afficher correctement les images et la css
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/accueil-encheres").authenticated();
                    auth.requestMatchers("/profil/*").authenticated();
                    auth.requestMatchers("/modifier-profil/*").authenticated();
                    auth.requestMatchers("/css/*").permitAll();
                    auth.requestMatchers("/images/*").permitAll();
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/logout").authenticated();
                    auth.requestMatchers("/view-encheres").permitAll();
                    auth.requestMatchers("/enchere-remporte").permitAll();
                    auth.requestMatchers("/enchere-termine").permitAll();
                    auth.requestMatchers("/inscription").permitAll();
                    auth.requestMatchers("/nouvelle-vente").permitAll();
                    auth.anyRequest().permitAll();
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("pseudo")
                        .passwordParameter("mot_de_passe")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/Liste")
                        .logoutSuccessUrl("/logout-sucess")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsManager userDetailsManager) throws Exception {
        System.out.println("userDetailsManager : " + userDetailsManager);
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userDetailsManager)
                .passwordEncoder(passwordEncoder());
        System.out.println("authenticationManager : " + authenticationManagerBuilder);

        return authenticationManagerBuilder.build();

    }

}