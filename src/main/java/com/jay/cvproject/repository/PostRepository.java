package com.jay.cvproject.repository;

import com.jay.cvproject.models.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
