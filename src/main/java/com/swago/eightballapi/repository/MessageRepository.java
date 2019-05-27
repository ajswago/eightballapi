package com.swago.eightballapi.repository;

import com.swago.eightballapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByName(String name);
}