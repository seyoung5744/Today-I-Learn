package chap05.실전연습;

import java.util.Objects;

public class Trader {

    private String name;
    private String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trader trade = (Trader) o;
        return Objects.equals(getName(), trade.getName()) && Objects.equals(getCity(), trade.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCity());
    }

    @Override
    public String toString() {
        return String.format("Trader:%s in %s", name, city);
    }
}
