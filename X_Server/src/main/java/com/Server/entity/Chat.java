package com.Server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "chats")
public class Chat {
    @Id
    private String _id;

    @DBRef
    private List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    @DBRef
    private List<Message> messageList = new ArrayList<>();

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public User getUser(int index) {
        return userList.get(index);
    }

    private boolean isSeen;

    @LastModifiedDate
    private Instant updatedAt;

    @CreatedDate
    private Instant createdAt;

    @Override
    public String toString() {
        return "Chat{" +
                "_id='" + _id + '\'' +
                ", userList=" + userList +
                ", messageList=" + messageList +
                ", isSeen=" + isSeen +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
