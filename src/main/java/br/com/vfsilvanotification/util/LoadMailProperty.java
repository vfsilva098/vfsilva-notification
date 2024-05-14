package br.com.vfsilvanotification.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.ByteArrayInputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Classe responsável pelo carregamento das configurações de envio de e-mail.
 *
 */
@Component
@Slf4j
@ApplicationScope
@RequiredArgsConstructor
public class LoadMailProperty {

    private final ResourceLoader resourceLoader;

    private Properties prop = null;

    /**
     * Realiza a captura de todas as configurações
     */
    public Properties getConfigurations() {

        if (prop == null) {
            loadConfiguration();
        }

        log.debug("props={}", prop);

        return prop;
    }

    /**
     * Realiza a captura de uma configuração específica.
     */
    public String getConfiguration(String key) {

        if (prop == null) {
            loadConfiguration();
        }

        return prop.getProperty(key);
    }

    /**
     * Realiza a captura das configurações com um prefixo específico. OBS.: Retira o prefixo da
     * propriedade.
     */
    public Properties getPrefixConfiguration(String prefix) {

        Properties _prop = new Properties();

        if (this.prop == null) {
            loadConfiguration();
        }

        Enumeration enuKeys = prop.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();

            if (key != null && key.startsWith("props")) {
                String _key = key.replaceAll(prefix + ".", "");
                _prop.put(_key, prop.getProperty(key));
            }
        }

        log.debug("props={}", _prop);

        return _prop;
    }

    /**
     * Realiza o carregamento das configurações.
     */
    private void loadConfiguration() {

        this.prop = null;
        Resource resource = resourceLoader.getResource("classpath:config-mail.properties");

        try (final var is = resource.getInputStream()) {

            this.prop = new Properties();
            this.prop.load(new ByteArrayInputStream(is.readAllBytes()));

            log.debug("props={}" + prop);


        } catch (Exception ex) {
            log.error("FALHA NO CARREGAMENTO DAS PROPRIEDADES DE CONFIGURAÇÃO DE E-MAIL");
            log.error("ERRO: {}", ex.getMessage());
        }
    }
}
