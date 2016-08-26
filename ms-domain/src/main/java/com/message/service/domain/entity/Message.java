package com.message.service.domain.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "text")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String text;
    @JsonIgnore
    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
    @OneToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "author", nullable = false)
    private User author;
    @OneToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "recipient", nullable = false)
    private User recipient;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty
    public Date getCreated() {
        return created;
    }

    @JsonIgnore
    public void setCreated(Date created) {
        this.created = created;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}
