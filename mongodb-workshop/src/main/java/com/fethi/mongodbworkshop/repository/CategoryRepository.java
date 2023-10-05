package com.fethi.mongodbworkshop.repository;

import com.fethi.mongodbworkshop.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category,String> {
}
