package br.com.vfsilvanotification.util;

import br.com.vfsilvanotification.dto.TemplateMailDTO;
import br.com.vfsilvanotification.exceptions.TemplateNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Component
@RequestScope
public class TemplateMail {

    List<TemplateMailDTO> templates;

    /**
     * Method to add html templates to send email.
     *
     * @param path   example 'email/create-user.html'
     * @param sender bean name SMTP config "default" is with gmail
     */
    public void addTemplate(final String path, final String htmlName, final String sender) {
        if (isNull(templates)) {
            templates = new ArrayList<>();
        }
        templates.add(TemplateMailDTO.builder()
                .path(path)
                .sender(sender == null ? "default" : sender)
                .htmlName(htmlName)
                .build());
    }

    public void addTemplates(final List<TemplateMailDTO> templates) {
        for (TemplateMailDTO dto : templates) {
            addTemplate(dto.getPath(), dto.getHtmlName(), dto.getSender());
        }
    }

    public TemplateMailDTO getTemplate(final String htmlName) {
        if (isNull(templates)) {
            return null;
        }

        return this.templates
                .parallelStream()
                .filter(templateMailDTO -> templateMailDTO.getHtmlName().equals(htmlName))
                .findFirst()
                .orElseThrow(() -> new TemplateNotFoundException("Template não existe."));
    }
}
