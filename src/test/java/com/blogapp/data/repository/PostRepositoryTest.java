package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void savePostToDBTest() {
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Lorem opsium");
        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
    }

    @Test
    void throwExceptionWhenSavingPostWithDuplicateTitle() {

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Lorem opsium");
        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();

        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech");
        blogPost2.setContent("Lorem opsium");
        log.info("Created a blog post --> {}", blogPost2);
        assertThrows(DataIntegrityViolationException.class, () -> postRepository.save(blogPost2));
    }

    @Test
    void whenPostIsSaved_thenSaveAuthor(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech");
        blogPost.setContent("Lorem opsium");
        log.info("Created a blog post --> {}", blogPost);

        Author author = new Author();
        author.setFirstname("John");
        author.setLastname("Wick");
        author.setEmail("john@mail.com");
        author.setPhoneNumber("0825825229");


        //map relationships
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("Blog post after saving --> {}", blogPost);

    }

    @Test
    void findAllPostInTheDbTest(){

        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void deletePostTest(){
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        System.out.printf("%s is a boy", "john");
        log.info("Post fetched from the database --> {}", savedPost);

        //delete post
        postRepository.deleteById(41);

        Post deletedPost = postRepository.findById(41).orElse(null);
        assertThat(deletedPost).isNull();
    }



    @Test
    void toTestThatPostCanUpdate() {
        Post savedPost = postRepository.findById(42).orElse(null);
        assertThat(savedPost).isNotNull();
        String oldTitle = savedPost.getTitle();
        savedPost.setTitle("New Title");
        postRepository.save(savedPost);
        savedPost = postRepository.findById(42).orElse(null);
        assertThat(savedPost).isNotNull();

        assertNotEquals(oldTitle, savedPost.getTitle());
    }

    @Test
    void updatePostAuthorTest(){
    Post savedPost postRepository.findById(41).orElse()}
}