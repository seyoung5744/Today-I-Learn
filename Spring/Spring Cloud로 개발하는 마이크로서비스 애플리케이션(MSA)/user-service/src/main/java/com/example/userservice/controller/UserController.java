package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.Users;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.CreateUserRequest;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping
@RestController
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final Greeting greeting;

    private final UserService userService;


    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service" +
                        ", port(local.server.port)=%s" +
                        ", port(server.port)=%s" +
                        ", token secret=%s" +
                        ", token expiration time=%s",
                env.getProperty("local.server.port"),
                env.getProperty("server.port"),
                env.getProperty("token.secret"),
                env.getProperty("token.expiration_time")
        );
    }

    @GetMapping("/welcome")
    public String welcome() {
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(request, UserDto.class);

        UserDto returnedUserDto = userService.createUser(userDto);

        UserResponse userResponse = mapper.map(returnedUserDto, UserResponse.class);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<Users> users = userService.getUserByAll();

        List<UserResponse> result = users.stream()
                .map(user -> new ModelMapper().map(user, UserResponse.class))
                .toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        UserResponse result = new ModelMapper().map(userDto, UserResponse.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
