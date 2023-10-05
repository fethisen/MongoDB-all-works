package com.fethi.mongodbworkshop.resources;

import com.fethi.mongodbworkshop.domain.Product;
import com.fethi.mongodbworkshop.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final SearchService searchService;

    public ProductController(SearchService searchService) {
        this.searchService = searchService;
    }
    @GetMapping("/search/is/{name}")
    public ResponseEntity<List<Product>> searchByName(@PathVariable("name") String name) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(searchService.searchByName(name));
        return response;
    }
    @GetMapping("/search/starts-with/{name}")
    public ResponseEntity<List<Product>> searchByNameStartsWith(@PathVariable("name") String name) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(searchService.searchByNameStartingWith(name));
        return response;
    }
    @GetMapping("/search/ends-with/{name}")
    public ResponseEntity<List<Product>> searchByNameEndsWith(@PathVariable("name") String name) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(searchService.searchByNameEndingWith(name));
        return response;
    }
    @GetMapping("/search/lt/{price}")
    public ResponseEntity<List<Product>> searchByPriceLt(@PathVariable("price") BigDecimal price) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(searchService.searchByPriceLessthan(price));
        return response;
    }
    @GetMapping("/search/gt/{price}")
    public ResponseEntity<List<Product>> searchByPriceGt(@PathVariable("price") BigDecimal price) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(searchService.searchByPriceGreaterthan(price));
        return response;
    }
    @GetMapping("/search/asc/{fieldname}")
    public ResponseEntity<List<Product>> searchByPriceGt(@PathVariable("fieldname") String fieldName) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(searchService.sortByFieldAsc(fieldName));
        return response;
    }
    @GetMapping("/sort-page/{fieldname}")
    public ResponseEntity<List<Product>> sortAndPage(@PathVariable("fieldname") String fieldName) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(searchService.sortAndPageByFieldAsc(fieldName));
        return response;
    }
    @GetMapping("/search/most-recent")
    public ResponseEntity<Product> searchMostRecentDate() {
        ResponseEntity<Product> response = ResponseEntity.ok(searchService.sortMostRecentDate());
        return response;
    }
}

















