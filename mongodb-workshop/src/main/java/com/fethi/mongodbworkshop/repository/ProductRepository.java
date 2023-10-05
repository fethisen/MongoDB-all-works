package com.fethi.mongodbworkshop.repository;

import com.fethi.mongodbworkshop.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAllByNameIgnoreCase(String name);

    List<Product> findAllByName(String name);

    Product findByName(String name);

    List<Product> findAllByNameStartingWith(String prefix);

    List<Product> findAllByNameEndingWith(String suffix);

    List<Product> findAllByNameContaining(String infix);

    List<Product> findAllByNameContainingIgnoreCase(String infix);

    // By default, order is in ascending order, so I have to specify Desc for descending order.
    List<Product> findAllByNameContainingIgnoreCaseOrderByPriceDesc(String fieldName);

    List<Product> findAllByNameContainingIgnoreCaseOrderByPriceDesc(String fieldName, Pageable pageable);

    List<Product> findAllByNameContainingIgnoreCaseOrderByPriceAsc(String fieldName, Pageable pageable);


    List<Product> findByPrice(BigDecimal price); // Finds exact match

    List<Product> findByPriceLessThan(BigDecimal price);

    List<Product> findByPriceLessThanEqual(BigDecimal price);

    List<Product> findByPriceGreaterThan(BigDecimal price);

    List<Product> findByPriceBetween(BigDecimal lowPrice, BigDecimal highPrice);

    List<Product> findByDateLessThanEqual(LocalDate date);

}
