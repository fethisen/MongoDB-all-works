package com.fethi.mongodbworkshop.service;


import com.fethi.mongodbworkshop.domain.Post;
import com.fethi.mongodbworkshop.repository.PostRepository;
import com.fethi.mongodbworkshop.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(String id){
        Optional<Post> user = postRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Post not found"));
    }
    public List<Post> findByTitle(String text) {
        return postRepository.searchTitle(text);
    }
    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return postRepository.fullSearch(text, minDate, maxDate);
    }

}
