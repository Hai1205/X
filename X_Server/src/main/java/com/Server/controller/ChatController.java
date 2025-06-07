package com.Server.controller;

import com.Server.dto.Response;
import com.Server.service.api.ChatApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatApi chatApi;

    @GetMapping("/get-user-chats/{userId}")
    public ResponseEntity<Response> getUserChats(@PathVariable String userId) {
        Response response = chatApi.getUserChats(userId);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/personal-chat/{chatId}/{senderId}/{receiverId}")
    public ResponseEntity<Response> personalChat(
            @PathVariable String chatId,
            @PathVariable String senderId,
            @PathVariable String receiverId,
            @RequestParam("content") String content
    ) {
        Response response = chatApi.personalChat(chatId, senderId, receiverId, content);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
