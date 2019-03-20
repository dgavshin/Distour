package sample.settings;

import java.io.Serializable;

public class UserMessage implements Serializable {

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String user;
    private String message;
    private String date;

    public UserMessage(String user, String message, String date) {
        this.message = message;
        this.user = user;
        this.date = date;


    }

}