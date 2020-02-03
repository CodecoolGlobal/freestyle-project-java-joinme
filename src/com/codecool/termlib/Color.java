package com.codecool.termlib;

public enum Color {
    BLACK(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    MAGENTA(5),
    CYAN(6),
    WHITE(7);

    private final int value;

    private Color(int value) {
        this.value = value;
    }

    public int getAsInt() {
        return value;
    }
}
