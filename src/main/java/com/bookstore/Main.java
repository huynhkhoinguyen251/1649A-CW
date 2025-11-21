package com.bookstore;

import com.bookstore.ds.Queue;
import com.bookstore.ds.Stack;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Demo Queue (FIFO) ===");
        demoQueue();

        System.out.println("\n=== Demo Stack (LIFO) ===");
        demoStack();
    }

    private static void demoQueue() {
        Queue<Integer> queue = new Queue<>(4);
        System.out.println("Queue rỗng? " + queue.isEmpty());

        System.out.println("Enqueue: 20, 30, 40");
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);
        System.out.println("Kích thước: " + queue.size());
        System.out.println("Peek: " + queue.peek());

        System.out.println("Dequeue lần lượt:");
        while (!queue.isEmpty()) {
            System.out.println(" -> " + queue.dequeue());
        }
        System.out.println("Queue rỗng? " + queue.isEmpty());
    }

    private static void demoStack() {
        Stack<String> stack = new Stack<>();
        System.out.println("Stack rỗng? " + stack.isEmpty());

        System.out.println("Push: A, B, C");
        stack.push("A");
        stack.push("B");
        stack.push("C");
        System.out.println("Kích thước: " + stack.size());
        System.out.println("Peek: " + stack.peek());
        System.out.println("ToString (hiện trạng): " + stack.toString());

        System.out.println("Pop lần lượt:");
        while (!stack.isEmpty()) {
            System.out.println(" -> " + stack.pop());
        }
        System.out.println("Stack rỗng? " + stack.isEmpty());
    }
}
