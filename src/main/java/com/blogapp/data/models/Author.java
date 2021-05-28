package com.blogapp.data.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String lastname;

    private String firstname;

    private String profession;


    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @OneToMany
    @ToString.Exclude
    private List<Post> posts;

    public void addPost(Post post){
        if (posts == null){
            posts = new ArrayList<>();
        }
        posts.add(post);
    }

}
