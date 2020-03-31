package ro.utcluj.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idmessage")
    private Integer idmessage;

    private String username;
    private String messagetext;
    private Date date;

    public Message() {
    }

    public Message(String username, String messagetext, Date date) {
        this.username = username;
        this.messagetext = messagetext;
        this.date = date;
    }

    public Integer getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(Integer idmessage) {
        this.idmessage = idmessage;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idmessage=" + idmessage +
                ", username='" + username + '\'' +
                ", messagetext='" + messagetext + '\'' +
                ", date=" + date +
                '}';
    }
}
