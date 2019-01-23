package io.zipcoder.tc_spring_poll_application.dto.error;

import java.util.List;
import java.util.Map;

public class ErrorDetail {

    private String title;
    private int status;
    private String detail;
    private Long timeStamp;
    private String developerMessage;
    private Map<String, List<ValidationError>> errors;

    public ErrorDetail() {
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {

        this.status = status;
    }

    public String getDetail() {

        return detail;
    }

    public void setDetail(String detail) {

        this.detail = detail;
    }

    public Long getTimeStamp() {

        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {

        this.timeStamp = timeStamp;
    }

    public String getDeveloperMessage() {

        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {

        this.developerMessage = developerMessage;
    }

    public Map<String, List<ValidationError>> getErrors() {

        return errors;
    }

    public void setErrors(Map<String, List<ValidationError>> errors) {
        this.errors = errors;
    }
}
