package com.jay.cvproject.repository;

import com.jay.cvproject.models.PostReview;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReviewRepository extends PagingAndSortingRepository<PostReview, Long> {
}
