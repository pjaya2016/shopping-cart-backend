package com.jay.cvproject.repository;

import com.jay.cvproject.models.Orders;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Orders, Long> {
}
