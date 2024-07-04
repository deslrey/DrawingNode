package org.deslre.loginModule.repository;

import org.deslre.loginModule.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    UserLogin findByUserName(String userName);

    Optional<UserLogin> findByUserNameAndPassWord(String userName, String passWord);


}
