package com.example.codelabsvc.repository;

import java.util.List;

public interface ChatCustomRepository {
    List<Integer> getAllChatIds(String sender);
}
