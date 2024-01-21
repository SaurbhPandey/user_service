package com.saurabh.userservice.Controller;

import com.saurabh.userservice.Dtos.LoginRequestDto;
import com.saurabh.userservice.Dtos.SignUpRequestDto;
import com.saurabh.userservice.Dtos.UserDto;
import com.saurabh.userservice.services.AuthService.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws Exception{
        return authService.login(request.getEmail() , request.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpRequestDto> signUp(@RequestBody SignUpRequestDto request){
        return authService.signUp(request.getEmail() , request.getPassword());
    }
}
