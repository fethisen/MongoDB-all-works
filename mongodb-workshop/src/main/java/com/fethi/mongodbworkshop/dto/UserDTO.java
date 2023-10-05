package com.fethi.mongodbworkshop.dto;


import com.fethi.mongodbworkshop.domain.User;

public class UserDTO {
    private String id;
    private String name;
    private String email;
    private Integer age;

    public UserDTO() {
    }
    public UserDTO(User obj) {
        id = obj.getId();
        name = obj.getName();
        email = obj.getEmail();
        age = obj.getAge();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
