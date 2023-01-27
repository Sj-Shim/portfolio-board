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
    @Column(insertable = false, updatable = false)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "userId")
    private User user;

    @Setter
    @Column(length = 500, nullable = false)
    private String content;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @Setter
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @OrderBy("createdDate desc")
    @ToString.Exclude
    private List<Comment> replies = new ArrayList<>();

    private Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
    }
    private Comment(Post post, User user, String content, Comment parent) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.parent = parent;
    }

    public static Comment of(Post post, User user, String content) {
        return new Comment(post, user, content);
    }
    public static Comment of(Post post, User user, String content, Comment parent) {
        return new Comment(post, user, content, parent);
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
