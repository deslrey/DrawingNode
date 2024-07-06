package org.deslre.loginModule.service;

import org.deslre.loginModule.entity.UserLoginEntity;
import org.deslre.utils.Results;

import java.util.List;

public interface UserLoginService {

    Results<List<UserLoginEntity>> finAll();

    Results<String> register(UserLoginEntity userLoginEntity);

    Results<String> loginUSer(UserLoginEntity userLoginEntity);
}
