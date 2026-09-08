package com.plant.farmeraiassistent.controller;

import com.plant.farmeraiassistent.dto.ChatRequest;
import com.plant.farmeraiassistent.dto.ChatResponse;
import com.plant.farmeraiassistent.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request) {

        return chatService.chat(
                request.getMessage()
        );
    }
}