package com.kh.board.domain;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

    @Setter
    @Column(length = 50, nullable = false, unique = true)
    private String slug;

    @OneToMany(mappedBy = "channel")
    @ToString.Exclude
    private final Set<Subscribe> subscribes = new LinkedHashSet<>();


    @Transient
    private Integer subCount = subscribes.size();

    private Channel(String channelName, String description, String slug) {
        this.channelName = channelName;
        this.description = description;
        this.slug = slug;
    }

    public static Channel of(String channelName, String description, String slug) {
        return new Channel(channelName, description, slug);
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
