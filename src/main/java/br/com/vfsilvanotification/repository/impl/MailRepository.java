package br.com.vfsilvanotification.repository.impl;

import br.com.vfsilvanotification.dto.TemplateMailDTO;
import br.com.vfsilvanotification.repository.IMailRepository;
import br.com.vfsilvanotification.util.ExtendedMail;
import br.com.vfsilvanotification.util.LoadMailProperty;
import br.com.vfsilvanotification.util.ResponseMail;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.CharEncoding;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.expression.ThymeleafEvaluationContext;

@Repository
@RequiredArgsConstructor
public class MailRepository implements IMailRepository {

    private final LoadMailProperty loadMailProperty;
    private final ApplicationContext applicationContext;
    private final TemplateEngine templateEngine;

    @Override
    public ResponseMail sendMail(ExtendedMail email, JavaMailSender javaMailSender) {

        email.getContext().setVariable(ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                new ThymeleafEvaluationContext(applicationContext, null));

        String body = templateEngine.process(email.getTemplate().getPath(), email.getContext());
        return submit(email.getTo(), email.getSubject(), body, true, email.getTemplate(), javaMailSender);
    }

    /**
     * Método interno responsável pelo envio de e-mail
     *
     * @param to
     * @param subject
     * @param text
     * @param isHtml
     * @return
     */
    private ResponseMail submit(String to, String subject, String text, Boolean isHtml, TemplateMailDTO template, JavaMailSender javaMailSender) {

        try {

            MimeMessage mail = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mail, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, CharEncoding.UTF_8);
            helper.setTo(to.split(";"));
            helper.setSubject(subject);
            helper.setText(text, isHtml);


            helper.setFrom(loadMailProperty.getConfiguration("props." + template.getSender() + ".mail.fromAddress"), "GESAP");

            javaMailSender.send(mail);
            return new ResponseMail().success();

        } catch (Exception e) {
            return new ResponseMail().error(e.getMessage());
        }
    }
}
