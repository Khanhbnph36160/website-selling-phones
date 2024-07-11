package com.demo.service;

import com.demo.model.Account;
import com.demo.model.Order;
import com.demo.model.OrderDetail;
import com.demo.model.Product;
import com.demo.repo.OrderDetailRepo;
import com.demo.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private OrderDetailRepo orderDetailRepository;

    @Transactional
    public Order createOrder(String address, Account account) {
        // Tạo một đơn hàng mới
        Order order = new Order();
        order.setAddress(address); // Thiết lập địa chỉ cho đơn hàng
        order.setCreatedate(new Date()); // Thiết lập ngày tạo đơn hàng
        order.setAccount(account); // Thiết lập tài khoản cho đơn hàng

        // Lưu đơn hàng vào cơ sở dữ liệu để lấy Orderid được tạo ra
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    @Transactional
    public void addOrderDetail(OrderDetail orderDetail) {
        // Lưu chi tiết đơn hàng vào cơ sở dữ liệu
        orderDetailRepository.save(orderDetail);
    }
}
