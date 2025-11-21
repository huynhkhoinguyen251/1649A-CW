package com.bookstore.alg;

import com.bookstore.model.Order;

import java.util.List;
import java.util.Map;

public class Searching {
    // Linear search by customer name
    public static Order linearSearchByCustomer(List<Order> orders, String customerName) {
        if (orders == null) return null;
        for (Order o : orders) {
            if (o.getCustomerName().contains(customerName)) return o;
        }
        return null;
    }

    // Map lookup by orderId (O(1) average)
    public static Order mapLookup(Map<String, Order> map, String orderId) {
        return map.get(orderId);
    }
}
