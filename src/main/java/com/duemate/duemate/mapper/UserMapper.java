package com.duemate.duemate.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.duemate.duemate.dto.UserRequest;
import com.duemate.duemate.dto.UserResponse;
import com.duemate.duemate.model.User;

@Component
public class UserMapper {

    public User convertRequestToUser(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDefaultCurrency(request.getCurrency());

        return user;
    }

    public UserResponse convertUserTResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setDefaultCurrency(user.getDefaultCurrency());

        return response;
    }

    public List<UserResponse> convertUserListTResponse(List<User> users) {
        List<UserResponse> responseList = new ArrayList<>();
        for (User user : users) {
            responseList.add(convertUserTResponse(user));
        }

        return responseList;
    }

}
