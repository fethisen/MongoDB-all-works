package com.fethi.mongodbworkshop.resources;

import com.fethi.mongodbworkshop.domain.Product;
import com.fethi.mongodbworkshop.service.QueryMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/query-products")
public class QueryMethodController {
    private final QueryMethodService queryMethodService;

    public QueryMethodController(QueryMethodService queryMethodService) {
        this.queryMethodService = queryMethodService;
    }
    @GetMapping("/search/is/{name}")
    public ResponseEntity<List<Product>> searchByName(@PathVariable("name") String name) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(queryMethodService.searchByName(name));
        return response;
    }

    @GetMapping("/search/starts-with/{name}")
    public ResponseEntity<List<Product>> searchByNameStartsWith(@PathVariable("name") String namePrefix) {
        ResponseEntity<List<Product>> response = ResponseEntity
                .ok(queryMethodService.searchByNameStartingWith(namePrefix));
        return response;
    }

    @GetMapping("/search/ends-with/{name}")
    public ResponseEntity<List<Product>> searchByNameEndsWith(@PathVariable("name") String nameSuffix) {
        ResponseEntity<List<Product>> response = ResponseEntity
                .ok(queryMethodService.searchByNameEndingWith(nameSuffix));
        return response;
    }

    @GetMapping("/search/containing/{name}")
    public ResponseEntity<List<Product>> searchByNameContaining(@PathVariable("name") String nameInfix) {
        ResponseEntity<List<Product>> response = ResponseEntity
                .ok(queryMethodService.searchByNameContaining(nameInfix));
        return response;
    }

    @GetMapping("/search/lt/{price}")
    public ResponseEntity<List<Product>> searchByPriceLt(@PathVariable("price") BigDecimal price) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(queryMethodService.searchByPriceLt(price));
        return response;
    }

    @GetMapping("/search/gt/{price}")
    public ResponseEntity<List<Product>> searchByPriceGt(@PathVariable("price") BigDecimal price) {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(queryMethodService.searchByPriceGt(price));
        return response;
    }

    @GetMapping("/search/between/low/{lowPrice}/high/{highPrice}")
    public ResponseEntity<List<Product>> searchByPriceGt(@PathVariable("lowPrice") BigDecimal lowPrice,
                                                         @PathVariable("highPrice") BigDecimal highPrice) {
        ResponseEntity<List<Product>> response = ResponseEntity
                .ok(queryMethodService.searchByPriceBetween(lowPrice, highPrice));
        return response;
    }

    @GetMapping("/search/name/{name}/order-by-price")
    public ResponseEntity<List<Product>> searchNameContainingAndSortAscByPrice(@PathVariable("name") String name) {
        ResponseEntity<List<Product>> response = ResponseEntity
                .ok(queryMethodService.searchNameContainingAndSortAscByPrice(name));
        return response;
    }

    @GetMapping("/search/highest-price/name-contains/{name}")
    public ResponseEntity<List<Product>> searchNameContainingAndSortDescByPricePageable(
            @PathVariable("name") String name) {
        ResponseEntity<List<Product>> response = ResponseEntity
                .ok(queryMethodService.searchNameContainingAndSortDescByPricePageable(name));
        return response;
    }

    @GetMapping("/search/lowest-price/name-contains/{name}")
    public ResponseEntity<List<Product>> searchNameContainingAndSortAscByPricePageable(
            @PathVariable("name") String name) {
        ResponseEntity<List<Product>> response = ResponseEntity
                .ok(queryMethodService.searchNameContainingAndSortAscByPricePageable(name));
        return response;
    }

    @GetMapping("/search/name-contains/{name}/page-number/{page-number}/size/{size}")
    public ResponseEntity<List<Product>> searchNameContainingAndSortAscByPricePageableWithParams(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page-number", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size) {
        ResponseEntity<List<Product>> response = ResponseEntity
                .ok(queryMethodService.searchNameContainingAndSortAscByPricePageable(name, pageNumber, size));
        return response;
    }

    @GetMapping("/search/most-recent-date") // this doesn't do what I want yet ????????????????????????????????????????????????
    public ResponseEntity<List<Product>> searchMostRecentDate() {
        ResponseEntity<List<Product>> response = ResponseEntity.ok(queryMethodService.sortMostRecentDate());
        return response; // I want to return the single most recent result
    }
}
