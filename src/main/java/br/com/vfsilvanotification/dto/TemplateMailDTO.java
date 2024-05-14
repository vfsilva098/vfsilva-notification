package br.com.vfsilvanotification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TemplateMailDTO {

    private String path;
    private String htmlName;
    private String sender;
}
