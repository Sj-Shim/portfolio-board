package com.kh.board.domain;

import com.kh.board.converter.ManagerLevelConverter;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
@Table(name = "CHANNELMANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "channel_slug")
    private Channel channel;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "user_userId")
    private User user;

    @Setter
    @Column(nullable = false)
    @Convert(converter = ManagerLevelConverter.class)
    private ManagerLevel managerLevel;

    private ChannelManager(Channel channel, User user, ManagerLevel managerLevel) {
        this.channel = channel;
        this.user = user;
        this.managerLevel = managerLevel;
    }

    public static ChannelManager of(Channel channel, User user, ManagerLevel managerLevel) {
        return new ChannelManager(channel, user, managerLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelManager that = (ChannelManager) o;
        return id.equals(that.id) && channel.equals(that.channel) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addChannelManager(this);
    }

    public void confirmChannel(Channel channel) {
        this.channel = channel;
        channel.addManager(this);
    }
}
