package com.kh.board.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends AuditingTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "channel_slug")
    @ToString.Exclude
    private Channel channel;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "user_userId")
    private User user;

//    @OneToOne(optional = true, fetch = FetchType.EAGER)
//    @Setter
//    private Category category;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer rating;

    @Setter
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer hit;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private Post(User user, Channel channel, String title, String content) {
        this.user = user;
        this.channel = channel;
        this.title = title;
        this.content = content;
    }
//    private Post(User user, Channel channel, /*Category category,*/String title, String content) {
//        this.user = user;
//        this.channel = channel;
////        this.category = category;
//        this.title = title;
//        this.content = content;
//    }

    public static Post of(User user, Channel channel, String title, String content) {
        return new Post(user, channel, title, content);
    }
//    public static Post of(User user, Channel channel, Category category, String title, String content) {
//        return new Post(user, channel, category, title, content);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addPost(this);
    }
    public void confirmChannel(Channel channel) {
        this.channel = channel;
        channel.addPost(this);
    }
    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateContent(String content) {
        this.content = content;
    }
}
