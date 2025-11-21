package com.bookstore.ds;

public class Stack<T> {
    private Node<T> top;
    private int size = 0;

    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    public void push(T value) {
        top = new Node<>(value, top);
        size++;
    }

    public T pop() {
        if (top == null) return null;
        T val = top.value;
        top = top.next;
        size--;
        return val;
    }

    public T peek() {
        return top == null ? null : top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> cur = top;
        sb.append("[top] ");
        while (cur != null) {
            sb.append(cur.value).append(" -> ");
            cur = cur.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
