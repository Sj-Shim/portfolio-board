package com.kh.board.domain;

import com.kh.board.repository.ChannelRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Objects;



//@Entity
@Getter
@ToString
@Table(name = "CATEGORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "slug")
    private Channel channel;

    @Setter
    @Column(nullable = false, length = 20)
    private String categoryName;

    @OneToOne(mappedBy = "category", fetch = FetchType.EAGER)
    private Post post;

    private Category(Channel channel, String categoryName) {
        this.channel = channel;
        this.categoryName = categoryName;
    }


    public static Category of(Channel channel, String categoryName) {
        return new Category(channel, categoryName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
