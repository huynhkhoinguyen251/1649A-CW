package com.bookstore.ds;

public class Queue<T> {
    private T[] data;
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public Queue(int capacity) {
        if (capacity <= 0) capacity = 16;
        data = (T[]) new Object[capacity];
    }

    public boolean enqueue(T item) {
        if (size == data.length) resize(data.length * 2);
        data[tail] = item;
        tail = (tail + 1) % data.length;
        size++;
        return true;
    }

    public T dequeue() {
        if (size == 0) return null;
        T val = data[head];
        data[head] = null;
        head = (head + 1) % data.length;
        size--;
        if (size > 0 && size == data.length / 4) resize(data.length / 2);
        return val;
    }

    public T peek() {
        return size == 0 ? null : data[head];
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newData = (T[]) new Object[Math.max(newCapacity, 4)];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(head + i) % data.length];
        }
        data = newData;
        head = 0;
        tail = size;
    }
}
