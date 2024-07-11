package com.demo.service;

import com.demo.model.Order;
import com.demo.model.OrderDetail;
import com.demo.model.Product;
import com.demo.repo.OrderDetailRepo;
import com.demo.repo.ProductRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@SessionScope
@Service
public class CartService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    OrderDetailRepo orderDetailRepo;

    List<OrderDetail> items = new ArrayList<>();

    public List<OrderDetail> getItems() {
        return items;
    }

    public void add(OrderDetail orderDetail) {
        Optional<OrderDetail> existingItem = items.stream()
                .filter(item -> item.getProduct().getId().equals(orderDetail.getProduct().getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            OrderDetail item = existingItem.get();
            item.setQuantity(item.getQuantity() + orderDetail.getQuantity());
            // Không cần thiết lập giá trị price ở đây vì đã có sẵn từ existingItem
            updateOrderDetail(item);
        } else {
            // Thiết lập giá trị price cho orderDetail trước khi thêm vào danh sách
            // Lấy giá sản phẩm từ cơ sở dữ liệu hoặc từ các thuộc tính của orderDetail nếu đã có sẵn
            orderDetail.setPrice(orderDetail.getProduct().getPrice());
            items.add(orderDetail);
            updateOrderDetail(orderDetail);
        }
    }


    public void remove(int id) {
        items.removeIf(item -> item.getProduct().getId().equals(id));
    }

    public void clear() {
        items.clear();
    }

    public void update(int id, int qty) {
        Optional<OrderDetail> itemToUpdate = items.stream()
                .filter(item -> item.getProduct().getId().equals(id))
                .findFirst();

        itemToUpdate.ifPresent(orderDetail -> {
            orderDetail.setQuantity(qty);
            // Không cần cập nhật lại giá tiền vì không có trường amount nữa
            updateOrderDetail(orderDetail);
        });
    }
    public double getTotalAmount() {
        List<OrderDetail> orderDetails = orderDetailRepo.findAll();
        double totalAmount = 0;
        for (OrderDetail orderDetail : orderDetails) {
            Integer price = orderDetail.getPrice();
            if (price != null) {
                double amount = price * orderDetail.getQuantity();
                totalAmount += amount;
            }
        }
        return totalAmount;
    }

    public int getTotal() {
        int total = 0;
        if (items != null) {
            for (OrderDetail item : items) {
                if (item != null && item.getQuantity() != null) {
                    total += item.getQuantity();
                }
            }
        }
        return total;
    }
    public double getAmount() {
        double total = 0;
        for (OrderDetail item : items) {
            Integer price = item.getProduct().getPrice();
            if (price != null && item.getQuantity() != null) {
                total += price.intValue() * item.getQuantity().intValue();
            }
        }
        return total;
    }
    private void updateOrderDetail(OrderDetail orderDetail) {
        try {
            orderDetailRepo.save(orderDetail);
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để kiểm tra và xử lý
            // Có thể thêm xử lý khác tùy theo yêu cầu cụ thể của ứng dụng
        }
    }
}
