package com.kh.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1151740983L;

    public static final QUser user = new QUser("user");

    public final QAuditingTimeEntity _super = new QAuditingTimeEntity(this);

    public final ListPath<ChannelManager, QChannelManager> channelManagerList = this.<ChannelManager, QChannelManager>createList("channelManagerList", ChannelManager.class, QChannelManager.class, PathInits.DIRECT2);

    public final SetPath<ChannelManager, QChannelManager> channelManagers = this.<ChannelManager, QChannelManager>createSet("channelManagers", ChannelManager.class, QChannelManager.class, PathInits.DIRECT2);

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final ListPath<Post, QPost> postList = this.<Post, QPost>createList("postList", Post.class, QPost.class, PathInits.DIRECT2);

    public final ListPath<Subscribe, QSubscribe> subscribes = this.<Subscribe, QSubscribe>createList("subscribes", Subscribe.class, QSubscribe.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

