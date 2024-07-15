package org.deslre.modules.loginmodule.repository;

import org.deslre.modules.loginmodule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}