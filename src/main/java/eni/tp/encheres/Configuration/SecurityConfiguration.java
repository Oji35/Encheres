
package eni.tp.encheres.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select pseudo, motDePasse from UTILISATEURS where pseudo=?");

        // Pour faire l'inscription
        jdbcUserDetailsManager.setCreateUserSql("INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0)");

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Restriction des URLs selon la connexion utilisateur et leurs rôles
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers(HttpMethod.GET,"/modifier-profil").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/nouvelle-vente").permitAll();

                    //Permettre à tous les utilisateurs d'afficher correctement les images et la css
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/accueil-encheres").permitAll();
                    auth.requestMatchers("/css/*").permitAll();
                    auth.requestMatchers("/images/*").permitAll();
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/view-encheres").permitAll();
                    auth.requestMatchers("/enchere-remporte").permitAll();
                    auth.requestMatchers("/enchere-termine").permitAll();
                    auth.requestMatchers("/inscription").permitAll();
                    auth.requestMatchers("/nouvelle-vente").permitAll();
                    auth.anyRequest().permitAll();
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/Liste")
                        .permitAll()
                ); // Custom login page URL

        return http.build();
    }

    private ServerHttpSecurity.FormLoginSpec formLogin() {

        return null;
    }
}