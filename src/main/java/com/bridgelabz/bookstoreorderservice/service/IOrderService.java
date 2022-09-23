package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.dto.OrderDTO;
import com.bridgelabz.bookstoreorderservice.model.OrderModel;
import com.bridgelabz.bookstoreorderservice.util.Response;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    public Response placeOrder(OrderDTO orderDTO, String token, Long bookId);
    public List<OrderModel> getAllOrders(String token);
    public Optional<OrderModel> getOrderById(Long orderId, String token);
    public Response cancleOrder(String token, Long orderId);
}
