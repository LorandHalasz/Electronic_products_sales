package ro.utcluj.api.dto;

import java.io.Serializable;
import java.sql.Date;

public class MessageBaseDTO implements Serializable {

    private Integer id;

    private String username;
    private String messagetext;
    private Date date;

    public MessageBaseDTO() {
    }

    public MessageBaseDTO(String username, String messagetext, Date date) {
        this.username = username;
        this.messagetext = messagetext;
        this.date = date;
    }

    public MessageBaseDTO(Integer id, String username, String messagetext, Date date) {
        this.id = id;
        this.username = username;
        this.messagetext = messagetext;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public String getMessageText() {
        return messagetext;
    }

    public void setMessageText(String messagetext) {
        this.messagetext = messagetext;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", username=" + username +
                ", messagetext='" + messagetext + '\'' +
                ", date=" + date +
                '}';
    }
}
