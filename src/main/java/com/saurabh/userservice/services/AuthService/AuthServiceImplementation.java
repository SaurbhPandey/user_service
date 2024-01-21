package com.saurabh.userservice.services.AuthService;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.saurabh.userservice.Dtos.SignUpRequestDto;
import com.saurabh.userservice.Dtos.UserDto;
import com.saurabh.userservice.Models.Session;
import com.saurabh.userservice.Models.SessionStatus;
import com.saurabh.userservice.Models.Users;
import com.saurabh.userservice.Repository.SessionRepository;
import com.saurabh.userservice.Repository.UserRepository;
import org.apache.catalina.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImplementation implements AuthService{
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthServiceImplementation(UserRepository userRepository , SessionRepository sessionRepository , BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public ResponseEntity<UserDto> login(String email, String password) throws Exception{
        Optional<Users> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            //user not found
            return null;
        }
        Users user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password , user.getPassword())){
            throw new RuntimeException("Incorrect Password , Enter Correct Password");
        }
        Session session = new Session();
        String token = RandomStringUtils.randomAlphabetic(30);
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);
        UserDto userDto = new UserDto();
        userDto = userDto.from(user);
        MultiValueMapAdapter<String , String> header = new MultiValueMapAdapter<>(new HashMap<>());
        header.add(HttpHeaders.SET_COOKIE ,"auth_token:" + token);
        ResponseEntity<UserDto> userResponse = new ResponseEntity<>(userDto , HttpStatus.OK);
        return userResponse;
    }

    @Override
    public ResponseEntity<SignUpRequestDto> signUp(String email, String password) {
        if(email.isEmpty() || password.isEmpty()){
            return null;
        }
        String encPassword = bCryptPasswordEncoder.encode(password);
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(encPassword);
        Users savedUser =  userRepository.save(user);
        SignUpRequestDto signUpResponse = new SignUpRequestDto();
        signUpResponse.setEmail(savedUser.getEmail());
        return new ResponseEntity<SignUpRequestDto>(signUpResponse , HttpStatus.CREATED);
    }
}
