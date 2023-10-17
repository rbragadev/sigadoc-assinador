package br.gov.mt.mti.siga.assinador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class SigaAssinadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigaAssinadorApplication.class, args);
    }

}
