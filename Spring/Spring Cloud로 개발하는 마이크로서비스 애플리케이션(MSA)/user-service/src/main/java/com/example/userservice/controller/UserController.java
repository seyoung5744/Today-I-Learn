package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
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

@Slf4j
@RequestMapping
@RestController
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final Greeting greeting;

    private final UserService userService;


    @GetMapping("/user-service/health-check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/user-service/welcome")
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
}
