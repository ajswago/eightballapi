package com.swago.eightballapi.controller;

import java.util.List;
import java.util.Optional;

import com.swago.eightballapi.model.Message;
import com.swago.eightballapi.service.MessageService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping(path = "", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 403, message = "Access Denied")
    })
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @GetMapping(path = "/random", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 403, message = "Access Denied")
    })
    public ResponseEntity<Message> getRandom() {
        return ResponseEntity.ok(messageService.getRandom());
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Access Denied")
    })
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Optional<Message> messageOptional = messageService.save(message);
        if (messageOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(messageOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{messageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(code = 204, response = void.class, message = "No Content"),
            @ApiResponse(code = 403, message = "Access Denied"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity deleteMessage(@PathVariable("messageId") Long messageId) {
        if (messageService.deleteById(messageId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}