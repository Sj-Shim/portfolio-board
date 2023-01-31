package com.kh.board.domain;

import lombok.*;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    private Post post;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "user_userId")
    private User user;

    @Setter
    @Column(length = 500, nullable = false)
    private String content;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @Setter
    private Comment parent;

    private boolean isRemoved = false;

    @OneToMany(mappedBy = "parent")
//    , fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    @OrderBy("createdDate desc")
    @ToString.Exclude
    private List<Comment> replies = new ArrayList<>();

    private Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
    }
    @Builder
    private Comment(Post post, User user, String content, Comment parent) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.parent = parent;
        this.isRemoved = false;
    }

    public static Comment of(Post post, User user, String content) {
        return new Comment(post, user, content);
    }
    public static Comment of(Post post, User user, String content, Comment parent) {
        return Comment.builder().post(post).user(user).content(content).parent(parent).build();
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

    public void confirmUser(User user) {
        this.user = user;
        user.addComment(this);
    }

    public void confirmPost(Post post) {
        this.post = post;
        post.addComment(this);
    }

    public void confirmParent(Comment parent){
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(Comment child) {replies.add(child);}

    public void updateContent(String content) {
        this.content = content;
    }
    public void remove() {this.isRemoved = true;}

    public List<Comment> findRemovableList() {
        List<Comment> result = new ArrayList<>();
        Optional.ofNullable(this.parent).ifPresentOrElse(
                parentComment -> {
                    if(parentComment.isRemoved()&& parentComment.isAllChildRemoved()){
                        result.addAll(parentComment.getReplies());
                        result.add(parentComment);
                    }
                },
                () -> {
                    if(isAllChildRemoved()){
                        result.add(this);
                        result.addAll(this.getReplies());
                    }
                }
        );
        return result;
    }

    private boolean isAllChildRemoved() {
        return getReplies().stream()
                .map(Comment::isRemoved)
                .filter(isRemove -> !isRemove)
                .findAny()
                .orElse(true);
    }
}
