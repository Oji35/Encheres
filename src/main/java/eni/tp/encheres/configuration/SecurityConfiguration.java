package eni.tp.encheres.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select pseudo,password,1 from utilisateur where pseudo=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select pseudo,role from roles where pseudo=?");

        return jdbcUserDetailsManager;
    }

    /**
     * Restriction des URLs selon la connexion utilisateur et leurs rôles
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( auth -> {

            auth.requestMatchers(HttpMethod.GET,"/creer").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/creer").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.GET,"/").permitAll();
            auth.requestMatchers(HttpMethod.GET,"/Liste").permitAll();
            auth.requestMatchers(HttpMethod.GET,"/detail").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/Liste").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/modifier").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.GET,"/modifier").hasRole("ADMIN");

            //Permettre à tous les utilisateurs d'afficher correctement les images et la css
            auth.requestMatchers("/css/*").permitAll();
            auth.requestMatchers("/images/*").permitAll();

            auth.anyRequest().permitAll();
        });
        //formulaire de connexion par défaut
        http.formLogin(Customizer.withDefaults()).formLogin(f -> f
                        .loginPage("/mylogin")
                        .defaultSuccessUrl("/")
                        .permitAll()
                        .failureUrl("/error"))
                .logout(logout -> logout
                        .invalidateHttpSession(true).clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/").permitAll());

        return http.build();


    }
}

