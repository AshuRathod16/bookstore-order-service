package com.bridgelabz.bookstoreorderservice.controller;

import com.bridgelabz.bookstoreorderservice.dto.OrderDTO;
import com.bridgelabz.bookstoreorderservice.model.OrderModel;
import com.bridgelabz.bookstoreorderservice.service.IOrderService;
import com.bridgelabz.bookstoreorderservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 22-09-2022
 * Purpose : controller for the Cart Service
 */

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;


    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to bookstore project";
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Response> placeOrder(@Valid @RequestBody OrderDTO orderDTO, @RequestHeader String token, @PathVariable Long orderId ) {
        Response response =orderService.placeOrder(orderDTO, token, orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<Response> geOrderById(@PathVariable Long orderId, @RequestHeader String token) {
        Optional<OrderModel> orderModel = orderService.getOrderById(orderId, token);
        Response response = new Response(200, "Cart items by id fetch successfully", orderModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<Response> getAllOrders(@RequestHeader String token) {
        List<OrderModel> orderModel = orderService.getAllOrders(token);
        Response response = new Response(200, "Get all cart items successfully", orderModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/cancleOrder/{id}")
    public ResponseEntity<Response> deleteFromCart(@RequestHeader String token, @PathVariable Long orderId) {
        Response response = orderService.cancleOrder(token, orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

