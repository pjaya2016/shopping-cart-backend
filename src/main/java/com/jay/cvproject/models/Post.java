package com.jay.cvproject.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdDate;
    private String title;
    private String description;
    private double price;
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private PostDetails details;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostReview> postReviews = new ArrayList<>();


}