package chap11._11_2;

import chap11.Insurance;
import java.util.Optional;

public class Car {

  private Optional<Insurance> insurance;

  public Optional<Insurance> getInsurance() {
    return insurance;
  }

}
