package com.dbmastery.jpapostgres.controller;

import com.dbmastery.jpapostgres.entity.UserEntity;
import com.dbmastery.jpapostgres.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserEntity> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserEntity> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
