package com.kh.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChannel is a Querydsl query type for Channel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChannel extends EntityPathBase<Channel> {

    private static final long serialVersionUID = 1864281157L;

    public static final QChannel channel = new QChannel("channel");

    public final QAuditingTimeEntity _super = new QAuditingTimeEntity(this);

    public final SetPath<ChannelManager, QChannelManager> channelManagers = this.<ChannelManager, QChannelManager>createSet("channelManagers", ChannelManager.class, QChannelManager.class, PathInits.DIRECT2);

    public final StringPath channelName = createString("channelName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<Post, QPost> postList = this.<Post, QPost>createList("postList", Post.class, QPost.class, PathInits.DIRECT2);

    public final StringPath slug = createString("slug");

    public final NumberPath<Integer> subCount = createNumber("subCount", Integer.class);

    public final SetPath<Subscribe, QSubscribe> subscribes = this.<Subscribe, QSubscribe>createSet("subscribes", Subscribe.class, QSubscribe.class, PathInits.DIRECT2);

    public QChannel(String variable) {
        super(Channel.class, forVariable(variable));
    }

    public QChannel(Path<? extends Channel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChannel(PathMetadata metadata) {
        super(Channel.class, metadata);
    }

}

