package com.ecommerce.backend.service;

import com.ecommerce.backend.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProvider {
    List<Order> getOrders(LocalDateTime from);
}
