package com.Server.dto;

import com.Server.entity.MessageStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class MessageDTO {
    private String _id;

    private String senderId;

    private String receiverId;

    private String content;

    private MessageStatus messageStatus;

    private Instant createdAt;
}
