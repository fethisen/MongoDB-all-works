package com.fethi.mongodbworkshop.resources;

import com.fethi.mongodbworkshop.domain.Post;
import com.fethi.mongodbworkshop.domain.User;
import com.fethi.mongodbworkshop.dto.UserDTO;
import com.fethi.mongodbworkshop.service.UserService;
import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> list = userService.findAll();
        List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO){
        User obj = userService.fromDTO(userDTO);
        obj = userService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody UserDTO userDTO, @PathVariable String id){
        User obj =userService.fromDTO(userDTO);
        obj.setId(id);
        obj = userService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/posts")
    public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
        User obj = userService.findById(id);
        return ResponseEntity.ok().body(obj.getPosts());
    }


    @GetMapping(value = "/{name}")
    public ResponseEntity<List<User>> getPersonStartWith(@PathVariable("name") String name) {
        return  ResponseEntity.ok().body(userService.getPersonStartWith(name));
    }

    @GetMapping("/minAge/{minAge}/maxAge/{maxAge}")
    public ResponseEntity<List<User>> getByPersonAge(@PathVariable Integer minAge,
                                                     @PathVariable Integer maxAge) {
        return ResponseEntity.ok().body(userService.getByPersonAge(minAge,maxAge));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable
                = PageRequest.of(page,size);
        return ResponseEntity.ok().body(userService.search(name,minAge,maxAge,city,pageable).getContent());
    }

    @GetMapping("/oldestPerson")
    public ResponseEntity<List<Document>> getOldestPerson() {
        return ResponseEntity.ok().body(userService.getOldestPersonByCity());
    }
    @GetMapping("/populationByCity")
    public ResponseEntity<List<Document>> getPopulationByCity() {
        return ResponseEntity.ok().body(userService.getPopulationByCity());
    }
}

