package chap03;

import jdk.jfr.BooleanFlag;

public class Apple implements Fruit {

    private Integer weight = 0;
    private Color color;

    public Apple() {}

    public Apple(Integer weight) {
        this.weight = weight;
    }

    public Apple(Integer weight, Color color) {
        this.weight = weight;
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString() {
        return String.format("Apple{color=%s, weight=%d}", color, weight);
    }
}
