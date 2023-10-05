package com.fethi.mongodbworkshop.repository;

import com.fethi.mongodbworkshop.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    List<User> findByNameStartsWith(String name);

    @Query(value = "{ 'age' : { $gt : ?0, $lt: ?1}}",
            fields = "{posts: 0, addresses: 0}")
    List<User> findUserByAgeBetween(Integer min, Integer max);
}
