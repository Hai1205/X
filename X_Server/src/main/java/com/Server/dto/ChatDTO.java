package com.Server.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChatDTO {
    private String _id;

    private List<UserDTO> userList = new ArrayList<>();

    private List<MessageDTO> messageList = new ArrayList<>();

    private boolean isSeen = true;

    @LastModifiedDate
    private Instant updatedAt;

    @CreatedDate
    private Instant createdAt;
}
