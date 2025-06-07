package com.Server.utils.mapper;

import com.Server.dto.ChatDTO;
import com.Server.entity.Chat;
import com.Server.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ChatMapper {
    public static ChatDTO mapEntityToDTO(Chat chat) {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.set_id(chat.get_id());
        chatDTO.setUserList(UserMapper.mapListEntityToListDTO(chat.getUserList()));
        chatDTO.setMessageList(MessageMapper.mapListEntityToListDTO(chat.getMessageList()));
        chatDTO.setSeen(chat.isSeen());
        chatDTO.setUpdatedAt(chat.getUpdatedAt());
        chatDTO.setCreatedAt(chat.getCreatedAt());

        return chatDTO;
    }

    public static List<ChatDTO> mapListEntityToListDTO(List<Chat> chatList) {
        return chatList.stream().map(ChatMapper::mapEntityToDTO).collect(Collectors.toList());
    }

    public static List<ChatDTO> mapListEntityToListDTOBetweenUserIdAndUsers(
            List<Chat> chatList,
            User user,
            String userId
    ) {
        return chatList.stream()
                .filter(chat -> (chat.getUser(1).get_id().equals(userId) &&
                        chat.getUser(2).get_id().equals(user.get_id())) ||
                        (chat.getUser(2).get_id().equals(userId) &&
                                chat.getUser(1).get_id().equals(user.get_id())))
                .map(ChatMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }
}
