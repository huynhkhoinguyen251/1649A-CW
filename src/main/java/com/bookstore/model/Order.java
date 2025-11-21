package com.bookstore.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private final String orderId;
    private String customerName;
    private String address;
    private List<BookItem> items;
    private boolean confirmed = false;

    public Order(String customerName, String address, List<BookItem> items) {
        this.orderId = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.address = address;
        this.items = items;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getAddress() { return address; }
    public List<BookItem> getItems() { return items; }
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }

    @Override
    public String toString() {
        return String.format("Order[%s] %s - %s: %d items - confirmed=%b",
                orderId, customerName, address, items == null ? 0 : items.size(), confirmed);
    }
}