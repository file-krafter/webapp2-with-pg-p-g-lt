package com.k8s.observebility.wa2.pgl.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.k8s.observebility.wa2.pgl.entity.Message;
import com.k8s.observebility.wa2.pgl.repo.MessageRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class MessageController {
	@Autowired
    private MessageRepository repository;
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @GetMapping("/messages")
    public List<Message> getAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/messages", consumes = {"multipart/form-data"})
    public Message create(@RequestParam("content") String content,
                          @RequestParam("image") MultipartFile file) throws IOException {

        logger.info("POST /api/messages");

        Message message = new Message();
        message.setContent(content);
        message.setImage(file.getBytes());

        return repository.save(message);
    }

    @GetMapping("/messages/{id}/image")
    public void getImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
    	logger.info("get api for /message/id/image");
        Optional<Message> msg = repository.findById(id);
        if (msg.isPresent() && msg.get().getImage() != null) {
            response.setContentType("image/jpeg");
            response.getOutputStream().write(msg.get().getImage());
            response.getOutputStream().flush();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}