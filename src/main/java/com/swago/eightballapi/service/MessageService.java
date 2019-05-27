package com.swago.eightballapi.service;

import com.swago.eightballapi.model.Message;
import com.swago.eightballapi.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service

@RequiredArgsConstructor
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message getRandom() {
        List<Message> messages = findAll();
        return messages.get(new Random(new Date().getTime()).nextInt(messages.size()));
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public Optional<Message> save(Message message) {
        List<Message> messages = messageRepository.findByName(message.getName());
        if (messages.isEmpty()) {
            return Optional.of(messageRepository.save(message));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteById(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}