package com.kh.board.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@Table(name = "CHANNEL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends AuditingTimeEntity{
    @Id @Column(length = 20)
    private String channelName;

    @Setter
    @Column(nullable = false)
    private String description;

    private Channel(String channelName, String description) {
        this.channelName = channelName;
        this.description = description;
    }

    public static Channel of(String channelName, String description) {
        return new Channel(channelName, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return channelName.equals(channel.channelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelName);
    }
}
