package org.deslre.loginModule.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "user_login", schema = "test", catalog = "")
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "user_name")
    private String userName;
    @Basic
    @Column(name = "pass_word")
    private String passWord;
    @Basic
    @Column(name = "exist")
    private Integer exist;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLogin that = (UserLogin) o;
        return id == that.id && Objects.equals(userName, that.userName) && Objects.equals(passWord, that.passWord) && Objects.equals(exist, that.exist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, passWord, exist);
    }
}
