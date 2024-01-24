package chap03;

public class Orange implements Fruit {

    private Integer weight = 0;
    private Color color;

    public Orange() {}

    public Orange(Integer weight) {
        this.weight = weight;
    }

    public Orange(Integer weight, Color color) {
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
        return String.format("Orange{color=%s, weight=%d}", color, weight);
    }

}
