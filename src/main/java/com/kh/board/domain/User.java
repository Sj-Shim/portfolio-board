package com.kh.board.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity{
    @Id @Column(length = 20)
    private String userId;

    @Column(nullable = false, length = 100)
    @Setter
    private String password;

    @Column(nullable = false, unique = true)
    @Setter
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    @Setter
    private String nickname;

    @Column(nullable = false)
    @ColumnDefault("3000")
    @Setter
    private Integer point;

    private User(String userId, String password, String email, String nickname) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }
    private User(String userId, String password, String email, String nickname, int point) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.point = point;
    }

    public static User of(String userId, String password, String email, String nickname) {
        return new User(userId, password, email, nickname);
    }
    public static User of(String userId, String password, String email, String nickname, int point) {
        return new User(userId, password, email, nickname, point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
