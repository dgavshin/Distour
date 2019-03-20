package sample.settings;

import java.io.Serializable;

public class UserMessageS implements Serializable {
    private String user;
    private String message;
    private String dateFrom;
    private String dateTo;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public UserMessageS(String user, String message, String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.message = message;
        this.user = user;

    }
}
