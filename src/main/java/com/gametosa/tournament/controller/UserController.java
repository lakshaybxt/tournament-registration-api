package com.gametosa.tournament.controller;

import com.gametosa.tournament.domain.entity.User;
import com.gametosa.tournament.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(@RequestBody String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok(userService.findAll());
    }
}
