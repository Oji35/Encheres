package eni.tp.encheres.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


public class LoginConfiguration {

    @Controller
    public static class LoginController {
        protected final Log logger = LogFactory.getLog(getClass());

        @GetMapping("/login")
        String login() {
            logger.info("Affichage du formulaire de login");
            return "login";
        }

    }

}
