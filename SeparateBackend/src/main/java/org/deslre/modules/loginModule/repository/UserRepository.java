package org.deslre.modules.loginModule.repository;

import org.deslre.modules.loginModule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE User u SET " +
            "u.nickName = CASE WHEN :#{#user.nickName} IS NULL THEN u.nickName ELSE :#{#user.nickName} END, " +
            "u.passWord = CASE WHEN :#{#user.passWord} IS NULL THEN u.passWord ELSE :#{#user.passWord} END, " +
            "u.updateTime = CURRENT_TIMESTAMP " +
            "WHERE u.id = :#{#user.id}")
    int updateUser(@Param("user") User user);


    User findUserByNickName(String nikeName);

}