package com.ecommerce.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document("recommendations")
public class Recommendation {
    @Id
    private String id;
    private String username;
    private List<Product> products;
}
