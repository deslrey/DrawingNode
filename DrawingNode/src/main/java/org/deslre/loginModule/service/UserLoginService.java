package org.deslre.loginModule.service;

import org.deslre.loginModule.entity.UserLogin;
import org.deslre.utils.Results;

import java.util.List;

public interface UserLoginService {

    Results<List<UserLogin>> finAll();

    Results<String> register(UserLogin userLogin);

    Results<String> loginUSer(UserLogin userLogin);
}
