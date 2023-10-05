package com.fethi.mongodbworkshop.service;

import com.fethi.mongodbworkshop.domain.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class SearchService {
    private final MongoTemplate mongoTemplate;

    public SearchService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Product> searchByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }
    public List<Product> searchByNameStartingWith(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^" + name, "i"));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    public List<Product> searchByNameEndingWith(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(name + "$", "i"));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    public List<Product> searchByPriceLessthan(BigDecimal price){
        Query query = new Query();
        query.addCriteria(Criteria.where("price").lt(price));
        List<Product> products = mongoTemplate.find(query,Product.class);
        return products;
    }

    public List<Product> searchByPriceGreaterthan(BigDecimal price){
        Query query = new Query();
        query.addCriteria(Criteria.where("price").gt(price));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }
    public List<Product> sortByFieldAsc(String fieldName){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, fieldName));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    public List<Product> sortAndPageByFieldAsc(String fieldName) {
        Query query = new Query();
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, fieldName));
        query.with(pageable);
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    public Product sortMostRecentDate() {
        Query query = new Query();
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "date"));
        query.with(pageable);
        List<Product> products = mongoTemplate.find(query, Product.class);
        if(products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }
}























