package com.bridgelabz.bookstoreorderservice.model;

import com.bridgelabz.bookstoreorderservice.dto.OrderDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "OrderDetails")
@Data
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private LocalDate orderDate;
    private Long orderQuantity;
    private double orderPrice;
    private String address;
    private Long userId;
    private Long bookId;
    private Boolean cancel;

    public OrderModel(OrderDTO orderDTO) {
        this.orderQuantity = orderDTO.getOrderQuantity();
        this.orderPrice = orderDTO.getOrderPrice();
        this.address = orderDTO.getAddress();
    }

    public OrderModel() {
    }
}
