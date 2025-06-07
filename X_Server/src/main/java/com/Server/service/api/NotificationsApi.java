package com.Server.service.api;

import com.Server.dto.*;
import com.Server.entity.*;
import com.Server.exception.OurException;
import com.Server.repo.*;
import com.Server.utils.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationsApi {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Response deleteNotificationById(String notificationId) {
        Response response = new Response();

        try {
            notificationRepository.findById(notificationId).orElseThrow(() -> new OurException("Notification Not Found"));
            notificationRepository.deleteById(notificationId);

            response.setStatusCode(200);
            response.setMessage("success");
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

    public Response getAllNotifications(int page, int limit, String sort, String order) {
        Response response = new Response();

        try {
            Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(direction, sort));

            Page<Notification> notificationPage = notificationRepository.findAll(pageable);
            List<NotificationDTO> notificationDTOList = NotificationMapper.mapListEntityToListDTO(notificationPage.getContent());

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setPagination(new Pagination(notificationPage.getTotalElements(), notificationPage.getTotalPages(), page));
            response.setNotificationList(notificationDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            System.out.println(e.getMessage());
        }

        return response;
    }

    public Response getUserNotifications(String userId) {
        Response response = new Response();

        try {
            userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            List<Notification> userNotifications = notificationRepository.findByTo(userId)
                    .stream()
                    .filter(notification -> !notification.getFrom().get_id().equals(userId))
                    .collect(Collectors.toList());
            List<NotificationDTO> notificationDTOList = NotificationMapper.mapListEntityToListDTO(userNotifications);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setNotificationList(notificationDTOList);
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

    public Response deleteNotificationByUserId(String userId) {
        Response response = new Response();

        try {
            userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));
            notificationRepository.deleteByFrom(userId);

            response.setStatusCode(200);
            response.setMessage("success");
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
