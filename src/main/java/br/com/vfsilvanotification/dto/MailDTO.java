package br.com.vfsilvanotification.dto;

import com.google.gson.Gson;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailDTO implements Serializable {

    private String to;
    private String subject;
    private String idTemplate;
    private Map<String, Object> context;

    public String toJson() {
        var body = new Gson().toJson(this);
        return body.replaceAll("^\"|\"$|\\\\", "");
    }
}

