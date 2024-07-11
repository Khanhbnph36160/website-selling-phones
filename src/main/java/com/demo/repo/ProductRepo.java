package com.demo.repo;

import com.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE "
            + "(:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword%) AND "
            + "(:categoryId IS NULL OR :categoryId = '' OR p.category.id = :categoryId) AND "
            + "(p.price BETWEEN :minPrice AND :maxPrice)")
    Page<Product> findProducts(@Param("keyword") String keyword,
                               @Param("categoryId") String categoryId,
                               @Param("minPrice") int minPrice,
                               @Param("maxPrice") int maxPrice,
                               Pageable pageable);
}
