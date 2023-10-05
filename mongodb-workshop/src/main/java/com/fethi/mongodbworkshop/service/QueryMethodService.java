package com.fethi.mongodbworkshop.service;

import com.fethi.mongodbworkshop.domain.Product;
import com.fethi.mongodbworkshop.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class QueryMethodService {
    private final ProductRepository productRepository;

    public QueryMethodService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchByName(String name){
        return productRepository.findAllByNameIgnoreCase(name);
    }

    public List<Product> searchByNameStartingWith(String namePrefix){
        return productRepository.findAllByNameStartingWith(namePrefix);
    }

    public List<Product> searchByNameEndingWith(String nameSuffix){
        return productRepository.findAllByNameEndingWith(nameSuffix);
    }
    public List<Product> searchByNameContaining(String nameInfix) {
        return productRepository.findAllByNameContainingIgnoreCase(nameInfix);
    }

    public List<Product> searchByPriceLt(BigDecimal price) {
        return productRepository.findByPriceLessThan(price);
    }

    public List<Product> searchByPriceGt(BigDecimal price) {
        return productRepository.findByPriceGreaterThan(price);
    }

    public List<Product> searchByPriceBetween(BigDecimal lowPrice, BigDecimal highPrice) {
        return productRepository.findByPriceBetween(lowPrice, highPrice);
    }
    public List<Product> searchNameContainingAndSortAscByPrice(String name) {
        return productRepository.findAllByNameContainingIgnoreCaseOrderByPriceDesc(name);
    }
    public List<Product> searchNameContainingAndSortDescByPricePageable(String name) {
        Pageable pageable = PageRequest.of(0, 1);
        return productRepository.findAllByNameContainingIgnoreCaseOrderByPriceDesc(name, pageable);
    }

    public List<Product> searchNameContainingAndSortAscByPricePageable(String name) {
        Pageable pageable = PageRequest.of(0, 1);
        return productRepository.findAllByNameContainingIgnoreCaseOrderByPriceAsc(name, pageable);
    }

    public List<Product> searchNameContainingAndSortAscByPricePageable(String name, int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return productRepository.findAllByNameContainingIgnoreCaseOrderByPriceAsc(name, pageable);
    }

    public List<Product> sortMostRecentDate() {
        return productRepository.findByDateLessThanEqual(LocalDate.now());
    }

}



























