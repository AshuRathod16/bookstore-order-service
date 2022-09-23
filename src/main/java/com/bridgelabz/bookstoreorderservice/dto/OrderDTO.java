package com.bridgelabz.bookstoreorderservice.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private Long orderQuantity;
    private Long orderPrice;
    private String address;
}
