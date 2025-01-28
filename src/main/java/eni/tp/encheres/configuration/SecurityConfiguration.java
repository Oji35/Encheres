package eni.tp.encheres.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;
import java.util.Locale;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, administrateur FROM UTILISATEURS WHERE pseudo=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT pseudo, 'ROLE_USER' FROM UTILISATEURS WHERE pseudo=?");

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
    SecurityFilterChain filterChain(HttpSecurity http, DataSourceTransactionManager dataSourceTransactionManager) throws Exception {

        http
                .authorizeHttpRequests(auth -> {

                    auth.anyRequest().permitAll();
                })
                .formLogin(withDefaults())

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/Liste")
                        .permitAll()
                ); // Custom login page URL

        return http.build();
    }

}


