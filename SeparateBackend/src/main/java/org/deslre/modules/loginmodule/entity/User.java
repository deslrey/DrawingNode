package org.deslre.modules.loginmodule.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tb_user", schema = "deslre")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "pass_word", length = 128)
    private String passWord;

    @Column(name = "nick_name", length = 32)
    private String nickName;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

}