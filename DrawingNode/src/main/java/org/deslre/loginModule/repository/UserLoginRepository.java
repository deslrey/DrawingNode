package org.deslre.loginModule.repository;

import org.deslre.loginModule.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long> {

    UserLoginEntity findByUserName(String userName);

    Optional<UserLoginEntity> findByUserNameAndPassWord(String userName, String passWord);

}
