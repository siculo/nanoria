package org.javamac.nanoria.core;

public enum Piece {
    BLACK, WHITE;

    public Piece next() {
        return values()[(ordinal() + 1) % values().length];
    }
}
