package com.jay.cvproject.models;

import javax.persistence.*;

@Entity
public class PostReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
