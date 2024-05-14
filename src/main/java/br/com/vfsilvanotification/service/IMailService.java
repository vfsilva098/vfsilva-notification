package br.com.vfsilvanotification.service;

import br.com.vfsilvanotification.dto.MailDTO;
import br.com.vfsilvanotification.util.ResponseMail;

public interface IMailService {

    ResponseMail send(MailDTO mail);
}
