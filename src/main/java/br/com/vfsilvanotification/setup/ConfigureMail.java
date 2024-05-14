package br.com.vfsilvanotification.setup;

import br.com.vfsilvanotification.util.LoadMailProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Enumeration;
import java.util.Properties;

/**
 * Classe responsável pela configuração de e-mail de envio.
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ConfigureMail {

    private final LoadMailProperty loadMailProperty;

    @Bean(value = "default")
    @Primary
    public JavaMailSender generateMailSenderOnBoard() {

        Properties prop = loadMailProperty.getConfigurations();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(prop.getProperty("email.default.host"));
        mailSender.setPort(Integer.parseInt(prop.getProperty("email.default.port")));

        mailSender.setUsername(prop.getProperty("email.default.userName"));
        mailSender.setPassword(prop.getProperty("email.default.password"));

        Properties props = mailSender.getJavaMailProperties();

        Properties extras = loadMailProperty.getPrefixConfiguration("props.default");
        Enumeration enuKeys = extras.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            props.setProperty(key, extras.getProperty(key));
        }

        return mailSender;
    }
}
