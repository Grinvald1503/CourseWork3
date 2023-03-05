package me.cw.coursework3.model;

public enum Color {
    RED("красный"),
    WHITE("белый"),
    BLACK("чёрный"),
    BLUE("синий"),
    YELLOW("жёлтый");
    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
