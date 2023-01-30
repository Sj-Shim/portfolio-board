package com.kh.board.domain;

import com.kh.board.domain.Subscribe;
import com.kh.board.domain.Channel;
import com.kh.board.domain.Post;
import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.Comment;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

@Table(name = "USER")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity implements Serializable {
    @Id @Column(length = 20, updatable = false)
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



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Subscribe> subscribes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<ChannelManager> channelManagers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChannelManager> channelManagerList = new ArrayList<>();

    private User(String userId, String password, String email, String nickname) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public static User of(String userId, String password, String email, String nickname) {
        return new User(userId, password, email, nickname);
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

    public void addPost(Post post) {
        postList.add(post);
    }
    public void addComment(Comment comment){
        commentList.add(comment);
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
    public void updateEmail(String email) {
        this.email = email;
    }
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getPassword());
    }


    public void addChannelManager(ChannelManager channelManager) {
        channelManagerList.add(channelManager);
    }

    public void removeSubscribe(Subscribe subscribe) {
        subscribes.remove(subscribe);
    }

    public void addSubscribe(Subscribe subscribe) {
        subscribes.add(subscribe);
    }
}
