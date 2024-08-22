package com.simple.bank_system.service.user;

import com.simple.bank_system.dtos.user.UserDto;
import com.simple.bank_system.models.user.UserModel;
import com.simple.bank_system.service.CRUDService;

public interface UserService extends CRUDService<UserModel, UserDto, Integer> {

    boolean existsByUsername(String username);
    UserModel readById(Integer id);
}
