package com.Server.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.Server.dto.Request;
//import com.Server.entity.Message;
//import com.Server.entity.User;
//import com.Server.service.api.ChatApi;
//import com.Server.utils.Utils;
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIONamespace;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.ConnectListener;
//import com.corundumstudio.socketio.listener.DataListener;
//import com.corundumstudio.socketio.listener.DisconnectListener;
//
//@Component
//public class SocketIOController {
//    @Getter
//    private SocketIONamespace namespace;
//    private Map<String, SocketIOClient> users = new HashMap<>();
//
//    @Autowired
//    private ChatApi chatApi;
//
//    @Autowired
//    public SocketIOController(SocketIOServer server) {
//        this.namespace = server.addNamespace("/chat");
//
//        this.namespace.addConnectListener(onConnectListener);
//        this.namespace.addDisconnectListener(onDisconnectListener);
//
//        this.namespace.addEventListener("userJoin", User.class, onUserJoinChat);
//        this.namespace.addEventListener("sendMessage", Request.class, onUserSendMessage);
//        this.namespace.addEventListener("userTyping", User.class, onUserTyping);
//        this.namespace.addEventListener("userStopTyping", User.class, onUserStopTyping);
//    }
//
//    public ConnectListener onConnectListener = new ConnectListener() {
//        @Override
//        public void onConnect(SocketIOClient client) {
//            System.out.println(client);
/// /			System.out.println("Client " + client.getSessionId() + " connected to /chat namespace.");
//        }
//    };
//
//    public DisconnectListener onDisconnectListener = new DisconnectListener() {
//        @Override
//        public void onDisconnect(SocketIOClient client) {
/// /			System.out.println("Client " + client.getSessionId() + " disconnected from /chat namespace.");
//            namespace.getBroadcastOperations().sendEvent("userLeft", users.get(client));
//            users.remove(client);
//            namespace.getBroadcastOperations().sendEvent("count", users.size());
//        }
//    };
//
//    public DataListener<User> onUserJoinChat = new DataListener<>() {
//        @Override
//        public void onData(SocketIOClient client, User user, AckRequest ackSender) throws Exception {
//            System.out.println("user "+user.get_id());
//            System.out.println(users.size());
//            users.put(user.get_id(), client);
//            namespace.getBroadcastOperations().sendEvent("newUser", user);
//            namespace.getBroadcastOperations().sendEvent("count", users.size());
//        }
//    };
//
//    public DataListener<Request> onUserSendMessage = new DataListener<>() {
//        @Override
//        public void onData(SocketIOClient client, Request request, AckRequest arg2) throws Exception {
//            String receiverId = request.getReceiverId(); // Người nhận tin nhắn
//            SocketIOClient receiverClient = users.get(receiverId); // Tìm client của người nhận
//
//            if (receiverClient != null) {
//                receiverClient.sendEvent("newMessage", request); // Chỉ gửi cho người nhận
//            } else {
//                System.out.println("⚠️ Người nhận chưa online: " + receiverId);
//            }
//
////            chatApi.personalChat(request.getChatId(), request.getSenderId(), receiverId, request.getContent());
//        }
//    };
//
//    public DataListener<User> onUserTyping = new DataListener<User>() {
//        @Override
//        public void onData(SocketIOClient client, User user, AckRequest arg2) throws Exception {
//            namespace.getBroadcastOperations().sendEvent("userTyping", client, user);
//        }
//    };
//
//    public DataListener<User> onUserStopTyping = new DataListener<User>() {
//        @Override
//        public void onData(SocketIOClient client, User user, AckRequest arg2) throws Exception {
//            namespace.getBroadcastOperations().sendEvent("userStopTyping", client, user);
//        }
//    };
//}

import com.Server.entity.Message;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SocketIOController {
    public SocketIOController(SocketIOServer server) {
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            sendMessage(data.get_id(),"get_message", senderClient, data);
        };
    }
    private void sendMessage(String room, String eventName, SocketIOClient senderClient, Message message) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(message.getSenderId(), message.getReceiverId(), message.getContent()));
            }
        }
    }

    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

}
