package com.fethi.mongodbworkshop.domain;

import com.fethi.mongodbworkshop.dto.AuthorDTO;
import com.fethi.mongodbworkshop.dto.CommentDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Post {
    @Id
    private String id;
    private Date date;
    private String title;
    private String body;
    private AuthorDTO authorDTO;
    List<CommentDTO> commentDTOS = new ArrayList<>();

    public Post() {
    }

    public Post(String id, Date date, String title, String body, AuthorDTO authorDTO) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.body = body;
        this.authorDTO = authorDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
    }

    public List<CommentDTO> getCommentDTOS() {
        return commentDTOS;
    }

    public void setCommentDTOS(List<CommentDTO> commentDTOS) {
        this.commentDTOS = commentDTOS;
    }
}
