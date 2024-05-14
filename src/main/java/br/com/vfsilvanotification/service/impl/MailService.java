package br.com.vfsilvanotification.service.impl;

import br.com.vfsilvanotification.dto.MailDTO;
import br.com.vfsilvanotification.dto.TemplateMailDTO;
import br.com.vfsilvanotification.repository.IMailRepository;
import br.com.vfsilvanotification.service.IMailService;
import br.com.vfsilvanotification.util.ExtendedMail;
import br.com.vfsilvanotification.util.ResponseMail;
import br.com.vfsilvanotification.util.TemplateMail;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class MailService implements IMailService {

    private final ApplicationContext context;
    private final IMailRepository repository;
    private final TemplateMail templateMail;

    @Override
    public ResponseMail send(MailDTO handler) {
        ResponseMail response = new ResponseMail();

        if (isNull(handler)) {
            response.addError("Objeto email está nulo.");
        }

        if (isBlank(handler.getTo())) {
            response.addError("Destinatário nulo ou vazio");
        }

        if (isBlank(handler.getSubject())) {
            response.addError("Assunto nulo ou vazio");
        }

        TemplateMailDTO templateMail = getTemplate(handler.getIdTemplate());

        if (templateMail == null) {
            response.addError("Template de e-mail inválido");
        }

        if (response.hasError()) {
            return response;
        }

        JavaMailSender sender = (JavaMailSender) context.getBean(templateMail.getSender());

        Context contexto = generateTemplate(handler.getContext());


        ExtendedMail mail = ExtendedMail
                .builder()
                .to(handler.getTo())
                .subject(handler.getSubject())
                .context(contexto)
                .template(templateMail)
                .build();

        return repository.sendMail(mail, sender);
    }

    /**
     * Captura de template de E-mail.
     */
    private TemplateMailDTO getTemplate(String idTemplate) {
        try {
            return templateMail.getTemplate(idTemplate);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Geração de Contexto para envio de e-mail.
     */
    private Context generateTemplate(Map<String, Object> mapa) {

        return Optional.ofNullable(mapa)
                .map(m -> new Context(null, m))
                .orElse(new Context());
    }
}
