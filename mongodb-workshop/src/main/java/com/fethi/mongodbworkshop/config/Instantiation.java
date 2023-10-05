package com.fethi.mongodbworkshop.config;

import com.fethi.mongodbworkshop.domain.Category;
import com.fethi.mongodbworkshop.domain.Post;
import com.fethi.mongodbworkshop.domain.Product;
import com.fethi.mongodbworkshop.domain.User;
import com.fethi.mongodbworkshop.dto.Address;
import com.fethi.mongodbworkshop.dto.AuthorDTO;
import com.fethi.mongodbworkshop.dto.CommentDTO;
import com.fethi.mongodbworkshop.repository.CategoryRepository;
import com.fethi.mongodbworkshop.repository.PostRepository;
import com.fethi.mongodbworkshop.repository.ProductRepository;
import com.fethi.mongodbworkshop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {
    private final UserRepository  userRepository;
    private final PostRepository postRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Instantiation(UserRepository userRepository, PostRepository postRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();


        Address addressFethi = new Address("Çardak sok","Turhal","Tokat");
        Address addressAli = new Address("Çardak sok","Turhal","İstanbul");
        Address addressMehmet = new Address("Çardak sok","Turhal","Ankara");
        Address addressFatih = new Address("Çardak sok","Turhal","Sivas");



        User fethi = new User(null,"Fethi", "fethi@gmail.com",32);
        User ali = new User(null,"Ali", "ali@gmail.com",30);
        User mehmet = new User(null,"Mehmet", "mehmet@gmail.com",36);
        User fatih = new User(null,"Fatih", "fatih@gmail.com",36);

        fethi.getAddresses().add(addressFethi);
        ali.getAddresses().add(addressAli);
        mehmet.getAddresses().add(addressMehmet);
        fatih.getAddresses().add(addressFatih);

        userRepository.saveAll(Arrays.asList(fethi,ali,mehmet,fatih));

        Post post1 = new Post(null, sdf.parse("21/05/2023"), "title 1","I am going to travel Tokat", new AuthorDTO(fethi));
        Post post2 = new Post(null, sdf.parse("23/05/2023"), "title 2","I woke up happy today ", new AuthorDTO(fethi));

        CommentDTO cm1 = new CommentDTO("Have a good trip",sdf.parse("21/05/2023"), new AuthorDTO(ali));
        CommentDTO cm2 = new CommentDTO("Enjoy",sdf.parse("22/05/2023"), new AuthorDTO(mehmet));
        CommentDTO cm3 = new CommentDTO("Have a great day",sdf.parse("23/05/2023"), new AuthorDTO(ali));

        post1.getCommentDTOS().addAll(Arrays.asList(cm1,cm2));
        post2.getCommentDTOS().addAll(Arrays.asList(cm3));

        postRepository.saveAll(Arrays.asList(post1,post2));
        fethi.getPosts().addAll(Arrays.asList(post1,post2));
        userRepository.save(fethi);

        Category categoryComputer = new Category(null,"Computer");
        Category categoryPhone = new Category(null,"Phone");

        categoryRepository.saveAll(Arrays.asList(categoryComputer,categoryPhone));

        Product product1 = new Product("aPhone 11", "iPhone 11", BigDecimal.valueOf(24.299).setScale(2, BigDecimal.ROUND_HALF_EVEN), 241, 5, LocalDate.now().minusMonths(4),categoryPhone);
        Product product2 = new Product("Poco C40", "Poco C40", BigDecimal.valueOf(3.699).setScale(2, BigDecimal.ROUND_HALF_EVEN), 147, 5, LocalDate.now().minusYears(1),categoryPhone);
        Product product3 = new Product("Xiaomi Redmi Note", "Xiaomi Redmi Note",  BigDecimal.valueOf(12.299).setScale(2, BigDecimal.ROUND_HALF_EVEN), 235, 5, LocalDate.now().minusYears(2),categoryPhone);
        Product product4 = new Product("Samsung Galaxy Z Flip5", "Samsung Galaxy Z Flip5",  BigDecimal.valueOf(47.999).setScale(2, BigDecimal.ROUND_HALF_EVEN), 256, 5, LocalDate.now().minusYears(3),categoryPhone);
        Product product5 = new Product("aPhone 13", "iPhone 13",  BigDecimal.valueOf(36.899).setScale(2, BigDecimal.ROUND_HALF_EVEN), 457, 5, LocalDate.now().minusYears(4),categoryPhone);
        Product product6 = new Product("Tcl 30 Plus", "Tcl 30 Plus",  BigDecimal.valueOf(5.999).setScale(2, BigDecimal.ROUND_HALF_EVEN), 541, 5, LocalDate.now().minusYears(5),categoryPhone);

        productRepository.saveAll(Arrays.asList(product1,product2,product3,product4,product5,product6));
    }
}
