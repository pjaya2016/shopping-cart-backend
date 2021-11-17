package com.jay.cvproject.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PostDetails {

    @Id
    @GeneratedValue
    private Long id;

    private Date createdOn;

    private String createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
