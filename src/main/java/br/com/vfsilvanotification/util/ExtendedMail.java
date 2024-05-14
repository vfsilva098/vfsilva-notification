package br.com.vfsilvanotification.util;

import br.com.vfsilvanotification.dto.TemplateMailDTO;
import lombok.*;
import org.thymeleaf.context.Context;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"to", "subject", "context", "template"})
public class ExtendedMail {

    private String to;
    private String subject;
    private Context context;
    private TemplateMailDTO template;

}
