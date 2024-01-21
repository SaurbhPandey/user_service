package com.saurabh.userservice.Dtos;

import com.saurabh.userservice.Models.Role;
import com.saurabh.userservice.Models.Users;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles = new HashSet<>();
    public static UserDto from(Users user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
