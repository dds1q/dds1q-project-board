package com.project.projectboard.repository;

import static org.assertj.core.api.Assertions.*;

import com.project.projectboard.config.JpaConfig;
import com.project.projectboard.domain.Article;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;



@DisplayName( "JPA 연결 테스트" )
@Import(JpaConfig.class )
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
        @Autowired ArticleRepository articleRepository,
        @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        List<Article> articles = articleRepository.findAll();

        assertThat( articles ).isNotNull().hasSize( 0 );
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        long previousCount = articleRepository.count();

        Article saveArticle = articleRepository.save
            ( Article.of( "title1" ,"content1" ,"#spring") );

        assertThat( articleRepository.count() ).isEqualTo( previousCount + 1 );


    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){

        Article article = articleRepository.save
            ( Article.of( "title1" ,"content1" ,"#spring") );
        String updateHashtag = "#springboot";

        article.setHashtag( updateHashtag );

        Article savedArticle = articleRepository.saveAndFlush( article );

        assertThat( savedArticle ).hasFieldOrPropertyWithValue( "hashtag" , updateHashtag);

    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){

        Article article = articleRepository.save
            ( Article.of( "title1" ,"content1" ,"#spring") );
        long previousCount = articleRepository.count();
        long previousCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();

        articleRepository.delete( article );

        assertThat( articleRepository.count() ).isEqualTo( previousCount - 1);
        assertThat( articleCommentRepository.count() ).isEqualTo( previousCommentCount-deletedCommentSize);



    }

}