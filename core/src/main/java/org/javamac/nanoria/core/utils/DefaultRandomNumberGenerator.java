package org.javamac.nanoria.core.utils;

public class DefaultRandomNumberGenerator implements RandomNumberGenerator {
    @Override
    public double generate(double max) {
        return Math.random() * max;
    }
}
