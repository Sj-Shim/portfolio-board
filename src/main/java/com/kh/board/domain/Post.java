package com.kh.board.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST")
public class Post extends AuditingTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "channelName")
    @Column(name = "channelName")
    private Channel channel;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "userId")
    @Column(name = "userId")
    private User user;

    @ManyToOne(optional = true)
    @Setter
    private Category category;

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

    @OrderBy("createdDate desc")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<Comment> comments = new LinkedHashSet<>();

    private Post(User user, Channel channel, String title, String content) {
        this.user = user;
        this.channel = channel;
        this.title = title;
        this.content = content;
    }
    private Post(User user, Channel channel, Category category,String title, String content) {
        this.user = user;
        this.channel = channel;
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public static Post of(User user, Channel channel, String title, String content) {
        return new Post(user, channel, title, content);
    }
    public static Post of(User user, Channel channel, Category category, String title, String content) {
        return new Post(user, channel, category, title, content);
    }

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
}
