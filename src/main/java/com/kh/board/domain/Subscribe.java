package com.kh.board.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
@Table(name = "SUBSCRIBE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscribe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "channel_slug")
    private Channel channel;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "user_userId")
    private User user;

    private Subscribe(Channel channel, User user) {
        this.channel = channel;
        this.user = user;
    }

    public static Subscribe of(Channel channel, User user) {
        return new Subscribe(channel, user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscribe subscribe = (Subscribe) o;
        return id.equals(subscribe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
