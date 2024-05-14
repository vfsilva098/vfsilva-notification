package br.com.vfsilvanotification.util;

import br.com.vfsilvanotification.enumerator.StatusMail;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

@ToString
public class ResponseMail {

    private StatusMail status;
    private ErrorMail errorStack;

    public ResponseMail success() {
        this.status = StatusMail.success;
        return this;
    }

    public ResponseMail error(String error) {
        this.status = StatusMail.error;
        this.errorStack = new ErrorMail();
        this.errorStack.addError(error);

        return this;
    }

    public StatusMail getStatus() {
        return status;
    }

    public void setStatus(StatusMail status) {
        this.status = status;
    }

    public ErrorMail getError() {
        return errorStack;
    }

    public ResponseMail addError(String error) {
        this.status = StatusMail.error;
        if (this.errorStack == null) {
            this.errorStack = new ErrorMail();
        }

        this.errorStack.addError(error);
        return this;
    }

    public boolean hasError() {
        return Objects.nonNull(errorStack) && !CollectionUtils.isEmpty(errorStack.getErrors());
    }
}
