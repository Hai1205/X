package com.Server.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "messages")
public class Message {
    public Message(String senderId, String receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    @Id
    private String _id;

    private String senderId;

    private String receiverId;

    private String content;

    private MessageStatus messageStatus;

    @CreatedDate
    private Instant createdAt;

    @Override
    public String toString() {
        return "Message{" +
                "_id='" + _id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                ", messageStatus=" + messageStatus +
                ", createdAt=" + createdAt +
                '}';
    }
}
