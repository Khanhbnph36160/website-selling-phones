package com.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name="Products")
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotBlank(message = "Vui lòng nhập tên")
    String name;
    @NotBlank(message = "Vui lòng nhập giá")
    Integer price;
    @ManyToOne @JoinColumn(name="categoryid")
    Category category;
    @NotBlank(message = "Vui lòng chọn ảnh")
    String image;
}
