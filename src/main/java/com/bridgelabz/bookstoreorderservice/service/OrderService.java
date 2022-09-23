package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.dto.OrderDTO;
import com.bridgelabz.bookstoreorderservice.exception.OrderException;
import com.bridgelabz.bookstoreorderservice.model.OrderModel;
import com.bridgelabz.bookstoreorderservice.repository.OrderRepository;
import com.bridgelabz.bookstoreorderservice.util.Response;
import com.bridgelabz.bookstoreorderservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    MailService mailService;

    @Autowired
    RestTemplate restTemplate;

    /**
     * @param token, noteDTO
     *               Purpose: Creating method to add items to cart
     * @author Ashwini Rathod
     */

    @Override
    public Response placeOrder(OrderDTO orderDTO, String token, Long bookId) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            boolean isBookPresent = restTemplate.getForObject("http://BOOKSTORE-BOOK-SERVICE:8083/verifybook/" + token + bookId, Boolean.class);
            if (isBookPresent) {
                OrderModel orderModel = new OrderModel(orderDTO);
                orderModel.setBookId(bookId);
                orderModel.setOrderDate(LocalDate.now());
                orderRepository.save(orderModel);
                return new Response(200, "Order Place Successfully", orderModel);
            }
            throw new OrderException(400, "Book Id is Not Present");

        }
        throw new OrderException(400, "User not Login, Please Check");
    }

    /**
     * @param token
     * purpose: Creating Method to get all cart items
     * @author Ashwini Rathod
     */

    @Override
    public List<OrderModel> getAllOrders(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            List<OrderModel> orderList = orderRepository.findAll();
            if (orderList.size() > 0) {
                return orderList;
        }
        throw new OrderException(400, "Order with this id not found");
    }

        throw new OrderException(400, "Token is invalid");
    }


    /**
     * @param token,cartId
     * purpose: Creating method to get order by id
     * @author Ashwini Rathod
     */

    @Override
    public Optional<OrderModel> getOrderById(Long orderId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<OrderModel> isUserIdPresent = orderRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<OrderModel> isNotePresent = orderRepository.findById(orderId);
                if (isNotePresent.isPresent()) {
                    return isNotePresent;
                }
            }
            throw new OrderException(400, "Order with this id not found");
        }
        throw new OrderException(400, "Token is invalid");
    }

    /**
     * @param token,cartId
     * purpose: Creating method to cancle order
     * @author Ashwini Rathod
     */


    @Override
    public Response cancleOrder(String token, Long orderId) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<OrderModel> isUserIdPresent = orderRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<OrderModel> isIdPresent = orderRepository.findById(orderId);
                if (isIdPresent.isPresent()) {
                    return new Response(200, "Order is Cancelled","Order id is:" + orderId);
                }
            }
            throw new OrderException(400, "User not found");
        }
        throw new OrderException(400, "Invalid token");
    }
}
