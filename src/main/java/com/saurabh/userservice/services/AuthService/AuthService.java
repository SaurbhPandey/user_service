package com.saurabh.userservice.services.AuthService;

import com.saurabh.userservice.Dtos.SignUpRequestDto;
import com.saurabh.userservice.Dtos.UserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
   ResponseEntity<UserDto> login(String email , String password) throws Exception;
   ResponseEntity<SignUpRequestDto> signUp(String email , String password);
}
