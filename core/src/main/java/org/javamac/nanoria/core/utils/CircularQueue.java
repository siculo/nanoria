package org.javamac.nanoria.core.utils;

public class CircularQueue<T> {
    private final T data[];
    private int current = 0;

    public CircularQueue(T... data) {
        this.data = data;
    }

    public boolean noData() {
        return data.length == 0;
    }

    public T next() {
        return noData() ? null: getNextData();
    }

    private T getNextData() {
        final T n = data[current];
        current = (current + 1) % data.length;
        return n;
    }
}
