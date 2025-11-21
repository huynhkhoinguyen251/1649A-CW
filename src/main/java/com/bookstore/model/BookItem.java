package com.bookstore.model;

public class BookItem {
    private String title;
    private String author;
    private int quantity;

    // Constructor
    public BookItem(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getQuantity() { return quantity; }

    // Setter for quantity (needed for stock deduction)
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s by %s (x%d)", title, author, quantity);
    }
}
