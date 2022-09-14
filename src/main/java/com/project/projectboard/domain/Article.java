package com.project.projectboard.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Getter
@ToString
@Table( indexes ={
    @Index( columnList = "title" ),
    @Index( columnList = "hashtag" ),
    @Index( columnList = "createdAt" ),
    @Index( columnList = "createdBy" )
} )
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Setter @Column( nullable = false )
    private String title;

    @Setter @Column( nullable = false, length = 10000 )
    private String content;

    @Setter private String hashtag;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany( mappedBy = "article", cascade = CascadeType.ALL )
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    public Article(){}

    private Article ( String title, String content, String hashtag ) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of( String title, String content, String hashtag ) {
        return new Article( title , content , hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
