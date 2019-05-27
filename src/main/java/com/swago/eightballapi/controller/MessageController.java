package com.swago.eightballapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.swago.eightballapi.model.Message;
import com.swago.eightballapi.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @GetMapping(path = "/random", produces = "application/json")
    public ResponseEntity<Message> getRandom() {
        return ResponseEntity.ok(messageService.getRandom());
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Optional<Message> messageOptional = messageService.save(message);
        if (messageOptional.isPresent()) {
            return ResponseEntity.ok(messageOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{messageId}")
    public ResponseEntity deleteMessage(@PathVariable("messageId") Long messageId) {
        if (messageService.deleteById(messageId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}