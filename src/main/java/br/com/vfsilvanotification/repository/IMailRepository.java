package br.com.vfsilvanotification.repository;

import br.com.vfsilvanotification.util.ExtendedMail;
import br.com.vfsilvanotification.util.ResponseMail;
import org.springframework.mail.javamail.JavaMailSender;

public interface IMailRepository {

    ResponseMail sendMail(ExtendedMail mail, JavaMailSender javaMailSender);
}
