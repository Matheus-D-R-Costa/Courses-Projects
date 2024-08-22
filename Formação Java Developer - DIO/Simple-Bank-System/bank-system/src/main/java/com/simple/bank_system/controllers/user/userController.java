package com.simple.bank_system.controllers.user;

import com.simple.bank_system.dtos.user.UserDto;
import com.simple.bank_system.models.user.UserModel;
import com.simple.bank_system.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class userController {

    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> readAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> readById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.readById(id));
    }

    @PostMapping
    public ResponseEntity<UserModel> create(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
