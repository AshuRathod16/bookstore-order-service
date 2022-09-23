package com.bridgelabz.bookstoreorderservice.repository;

import com.bridgelabz.bookstoreorderservice.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    @Query("select o from OrderModel o")
    Optional<OrderModel> findByUserId(Long userId);
}
