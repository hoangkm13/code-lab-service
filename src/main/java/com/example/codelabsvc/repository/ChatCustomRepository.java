package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Chat;

import java.util.List;

public interface ChatCustomRepository {
    List<Chat> getAllChatIds(String sender);
}
