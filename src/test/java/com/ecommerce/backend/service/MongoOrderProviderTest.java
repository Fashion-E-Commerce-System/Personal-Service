package com.ecommerce.backend.service;

import com.ecommerce.backend.domain.Order;
import com.ecommerce.backend.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@Import(MongoOrderProvider.class)
public class MongoOrderProviderTest {

    @Autowired
    private MongoOrderProvider mongoOrderProvider;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String TRANSACTIONS_COLLECTION = "orders";

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(TRANSACTIONS_COLLECTION);
        // Insert test data
        LocalDateTime now = LocalDateTime.now();
        mongoTemplate.save(createOrder("user1", now.minusDays(1), "prod1"), TRANSACTIONS_COLLECTION);
        mongoTemplate.save(createOrder("user1", now.minusWeeks(2), "prod2"), TRANSACTIONS_COLLECTION);
        mongoTemplate.save(createOrder("user2", now.minusMonths(2), "prod3"), TRANSACTIONS_COLLECTION); // Should not be retrieved
    }

    @Test
    void testGetOrders() {
        // When
        List<Order> orders = mongoOrderProvider.getOrders(LocalDateTime.now());

        // Then
        assertNotNull(orders);
        assertEquals(2, orders.size()); // Expecting 2 orders from the last 5 weeks
    }

    private Order createOrder(String username, LocalDateTime date, String... productIds) {
        List<Product> items = new ArrayList<>();
        for (String productId : productIds) {
            items.add(new Product(productId, 2));
        }
        return new Order(UUID.randomUUID().toString(), date.toLocalDate(), username, items);
    }
}