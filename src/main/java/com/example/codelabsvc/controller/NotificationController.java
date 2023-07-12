package com.example.codelabsvc.controller;

import com.example.codelabsvc.controller.response.comment.SaveNotificationRequestDTO;
import com.example.codelabsvc.controller.response.notification.NotificationResponse;
import com.example.codelabsvc.entity.Notification;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/notification")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ApiResponse<Notification>  saveNotification(@RequestBody SaveNotificationRequestDTO dto) throws CustomException {
        return ApiResponse.successWithResult(notificationService.saveNotification(dto));
    }

    @PutMapping("{notificationId}")
    public ApiResponse<Notification>  updateNotification(@PathVariable String notificationId) {
        return ApiResponse.successWithResult(notificationService.updateNotification(notificationId));
    }

    @PutMapping("")
    public ApiResponse<List<Notification>> markAsReadAll(){
        return ApiResponse.successWithResult(notificationService.markAsReadAll());
    }

    @GetMapping("")
    public ApiResponse<Page<NotificationResponse>> getAllNotification(@RequestParam(required = false, defaultValue = "1") int page,
                                                                      @RequestParam(required = false, defaultValue = "5") int size) {
        return ApiResponse.successWithResult(notificationService.getAllNotification(page, size));
    }

    @DeleteMapping("{notificationId}")
    public ApiResponse<Notification> deleteNotification(@PathVariable String notificationId) throws CustomException {
        return ApiResponse.successWithResult(notificationService.deleteNotification(notificationId));
    }
}
