package com.Server.utils.mapper;

import com.Server.dto.MessageDTO;
import com.Server.entity.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper {
    public static MessageDTO mapEntityToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.set_id(message.get_id());
        messageDTO.setSenderId(message.getSenderId());
        messageDTO.setReceiverId(message.getReceiverId());
        messageDTO.setContent(message.getContent());
        messageDTO.setCreatedAt(message.getCreatedAt());

        return messageDTO;
    }

    public static List<MessageDTO> mapListEntityToListDTO(List<Message> messageList) {
        return messageList.stream().map(MessageMapper::mapEntityToDTO).collect(Collectors.toList());
    }
}
