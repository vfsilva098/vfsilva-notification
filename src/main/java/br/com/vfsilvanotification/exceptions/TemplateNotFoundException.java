package br.com.vfsilvanotification.exceptions;

public class TemplateNotFoundException extends RuntimeException {

    public TemplateNotFoundException(final String error) {
        super(error);
    }
}
