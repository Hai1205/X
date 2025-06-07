package com.Server.dto;

import lombok.Data;

@Data
public class Request {
    //Auth
    private String email;

    private String username;

    private String password;

    // Post
    private String postId;

    private String userId;

    private String text;

    // User
    private String fullName;

    private String newPassword;

    private String confirmPassword;

    private String currentPassword;

    private String bio;

    private String link;

    // Chat
    private String chatId;

    private String senderId;

    private String receiverId;

    private String content;
}
