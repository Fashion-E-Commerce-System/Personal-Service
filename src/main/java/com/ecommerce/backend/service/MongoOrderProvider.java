package com.ecommerce.backend.service;

import com.ecommerce.backend.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("mongoOrderProvider")
@AllArgsConstructor
public class MongoOrderProvider implements OrderProvider {
    private final MongoTemplate mongoTemplate;
    private static final int WEEKS_TO_QUERY = 5;

    @Override
    public List<Order> getOrders(LocalDateTime from) {
        LocalDateTime startDate = from.minusWeeks(WEEKS_TO_QUERY);
        Query query = new Query(Criteria.where("date").gte(startDate));
        return mongoTemplate.find(query, Order.class);
    }
}
