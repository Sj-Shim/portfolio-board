package com.kh.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "COMMENT")
public class Comment extends AuditingTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private Post post;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "userId")
    private User user;

    @Setter
    @Column(length = 500, nullable = false)
    private String content;

    @JoinColumn(name = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentId;

    @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @OrderBy("createdDate desc")
    @ToString.Exclude
    private List<Comment> child = new ArrayList<>();

    private Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
    }
    private Comment(Post post, User user, String content, Comment parentId) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.parentId = parentId;
    }

    public static Comment of(Post post, User user, String content) {
        return new Comment(post, user, content);
    }
    public static Comment of(Post post, User user, String content, Comment parentId) {
        return new Comment(post, user, content, parentId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
