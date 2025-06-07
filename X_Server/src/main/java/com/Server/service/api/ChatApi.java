package com.Server.service.api;

import com.Server.dto.ChatDTO;
import com.Server.dto.Response;
import com.Server.dto.UserDTO;
import com.Server.entity.Chat;
import com.Server.entity.Message;
import com.Server.entity.User;
import com.Server.exception.OurException;
import com.Server.repo.ChatRepository;
import com.Server.repo.MessageRepository;
import com.Server.repo.UserRepository;
import com.Server.utils.mapper.ChatMapper;
import com.Server.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatApi {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Response getUserChats(String userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new OurException("User Not Found"));

            // Lấy danh sách người dùng mà userId đã follow
            List<User> followedUsers = user.getFollowingList();

            // Lấy danh sách các cuộc trò chuyện liên quan đến userId
            List<Chat> chats = chatRepository.findByUserList__id(userId);

            // Dùng HashMap để tránh trùng lặp
            Map<String, User> uniqueUsersMap = new LinkedHashMap<>();

            // Thêm người dùng đã follow vào map
            for (User followedUser : followedUsers) {
                uniqueUsersMap.put(followedUser.get_id(), followedUser);
            }

            // Thêm người từ danh sách chat nếu chưa có
            for (Chat chat : chats) {
                List<User> userList = chat.getUserList();

                for (User userInChat : userList) {
                    if (!userInChat.get_id().equals(userId)) {
                        uniqueUsersMap.putIfAbsent(userInChat.get_id(), userInChat);
                    }
                }
            }

            // Convert map thành danh sách
            List<User> uniqueUsers = new ArrayList<>(uniqueUsersMap.values());

            // Chuyển đổi danh sách User thành UserDTO
            List<UserDTO> userDTOs = UserMapper.mapListEntityToListDTOWithChatList(uniqueUsers, chats, userId);

            response.setStatusCode(200);
            response.setMessage("success");
            response.setUserList(userDTOs);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            System.out.println(e.getMessage());
        }

        return response;
    }

    public Response personalChat(String chatId, String senderId, String receiverId, String content) {
        Response response = new Response();

        try {
            User sender = userRepository.findById(senderId).orElseThrow(() -> new OurException("Sender Not Found"));
            User receiver = userRepository.findById(receiverId).orElseThrow(() -> new OurException("Receiver Not Found"));
            if (senderId.equals(receiverId)) {
                response.setStatusCode(400);
                response.setMessage("You can't chat yourself");

                return response;
            }

            Chat chat = chatRepository.findById(chatId).orElse(new Chat());
            if (chat.get_id() == null) {
                chat.addUser(sender);
                chat.addUser(receiver);
                chatRepository.save(chat);

                sender.addChat(chat);
                userRepository.save(sender);

                receiver.addChat(chat);
                userRepository.save(receiver);
            }

            Message message = new Message(senderId, receiverId, content);
            chat.addMessage(message);
            messageRepository.save(message);


            ChatDTO chatDTO = ChatMapper.mapEntityToDTO(chat);

            response.setStatusCode(200);
            response.setMessage("success");
            response.setChat(chatDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            System.out.println(e.getMessage());
        }

        return response;
    }
}
