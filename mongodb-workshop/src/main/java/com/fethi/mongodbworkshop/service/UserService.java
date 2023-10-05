package com.fethi.mongodbworkshop.service;

import com.fethi.mongodbworkshop.domain.User;
import com.fethi.mongodbworkshop.dto.UserDTO;
import com.fethi.mongodbworkshop.repository.UserRepository;
import com.fethi.mongodbworkshop.service.exception.ObjectNotFoundException;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public UserService(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> findAll(){return userRepository.findAll();}

    public User findById(String id){
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }
    public User insert(User obj){return userRepository.insert(obj);}
    public void delete(String id){userRepository.deleteById(id);}
    public User update(User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        return userRepository.save(newObj);
    }
    public List<User> getPersonStartWith(String name){return userRepository.findByNameStartsWith(name);}
    public List<User> getByPersonAge(Integer minAge, Integer maxAge) {
        return userRepository.findUserByAgeBetween(minAge,maxAge);
    }

    public Page<User> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable){
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();
        if (name != null && !name.isEmpty()){
            criteria.add(Criteria.where("name").regex(name,"i"));
        }
        if (minAge != null && maxAge !=null){
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if (city != null && !city.isEmpty()){
            criteria.add(Criteria.where("addresses.city").is(city));
        }
        if (!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        Page<User> people = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, User.class
                ), pageable, () -> mongoTemplate.count(query.skip(0).limit(0),User.class));
        return people;
    }

    public List<Document> getOldestPersonByCity(){
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");
        GroupOperation groupOperation = Aggregation.group("addresses.city")
                .first(Aggregation.ROOT)
                .as("oldestPerson");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);
        List<Document> person = mongoTemplate.aggregate(aggregation, User.class, Document.class).getMappedResults();
        return person;
    }

    public List<Document> getPopulationByCity(){
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        GroupOperation groupOperation = Aggregation.group("addresses.city")
                .count().as("popCount");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "popCount");
        ProjectionOperation projectionOperation =
                Aggregation.project()
                        .andExpression("_id").as("city")
                        .andExpression("popCount").as("count")
                        .andExclude("_id");

        Aggregation aggregation
                = Aggregation.newAggregation(unwindOperation,groupOperation,sortOperation,projectionOperation);

        List<Document> documents
                = mongoTemplate.aggregate(aggregation,
                User.class,
                Document.class).getMappedResults();
        return  documents;

    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }
    public User fromDTO(UserDTO objDTO) {
        return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail(),objDTO.getAge());
    }
}
