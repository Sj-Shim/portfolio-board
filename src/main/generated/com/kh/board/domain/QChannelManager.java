package com.kh.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChannelManager is a Querydsl query type for ChannelManager
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChannelManager extends EntityPathBase<ChannelManager> {

    private static final long serialVersionUID = -772107352L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChannelManager channelManager = new QChannelManager("channelManager");

    public final QChannel channel;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final EnumPath<ManagerLevel> managerLevel = createEnum("managerLevel", ManagerLevel.class);

    public final QUser user;

    public QChannelManager(String variable) {
        this(ChannelManager.class, forVariable(variable), INITS);
    }

    public QChannelManager(Path<? extends ChannelManager> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChannelManager(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChannelManager(PathMetadata metadata, PathInits inits) {
        this(ChannelManager.class, metadata, inits);
    }

    public QChannelManager(Class<? extends ChannelManager> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channel = inits.isInitialized("channel") ? new QChannel(forProperty("channel")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

