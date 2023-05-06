package dev.wandika.springbootdemo.user.controller;

import dev.wandika.springbootdemo.user.model.dto.User;
import dev.wandika.springbootdemo.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/bank-user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User request) {
        log.info("Creating user with {}", request.toString());
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        log.info("Updating user with {}", user.toString());
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @GetMapping
    public ResponseEntity readUsers(Pageable pageable) {
        log.info("Reading all users from API");
        return ResponseEntity.ok(userService.readUsers(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity readUser(@PathVariable("id") UUID id) {
        log.info("Reading user by id {}", id);
        return ResponseEntity.ok(userService.readUser(id));
    }

    @GetMapping(value = "/by_balance")
    public ResponseEntity findByBalance(@RequestParam(value = "fromBalance", required = false) BigDecimal fromBalance,
                                        @RequestParam(value = "toBalance", required = false) BigDecimal toBalance) {
        return ResponseEntity.ok(userService.findByBalance(fromBalance, toBalance));
    }
}

