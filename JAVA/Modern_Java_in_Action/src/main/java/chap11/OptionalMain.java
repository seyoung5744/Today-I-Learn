package chap11;

import chap11._11_1.CarV1;
import chap11._11_1.PersonV1;
import chap11._11_2.Car;
import chap11._11_2.Person;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OptionalMain {

    public String getCarInsuranceNameNullSafeV1(PersonV1 person) {
        if (person != null) {
            CarV1 car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

    public String getCarInsuranceNameNullSafeV2(PersonV1 person) {
        if (person == null) {
            return "Unknown";
        }
        CarV1 car = person.getCar();
        if (car == null) {
            return "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }
        return insurance.getName();
    }

    // 컴파일되지 않음:
    // (1)에서 Optional<Person>에 map(Person::getCar) 호출을 시도함. flatMap()을 이용하면 문제가 해결됨.
    // 그리고 (2)에서 Optional<Car>에 map(Car::getInsurance) 호출을 시도함. flatMap()을 이용하면 문제가 해결됨.
    // Insurance::getName은 평범한 문자열을 반환하므로 추가 "flatMap"은 필요가 없음.
   /* public String getCarInsuranceName(Person person) {
        Optional<Person> optPerson = Optional.of(person);
        Optional<String> name = optPerson.map(Person::getCar) // (1)
            .map(Car::getInsurance) // (2)
            .map(Insurance::getName);
        return name.orElse("Unknown");
    }*/

    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
            .flatMap(Car::getInsurance)
            .map(Insurance::getName)
            .orElse("Unknown");
    }

    public Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
            .map(Person::getCar) // 사람 목록을 각 사람이 보유한 자동차의 Optional<Car> 스트림으로 변환
            .map(optCar -> optCar.flatMap(
                Car::getInsurance)) // FlatMap 연산을 이용해 Optional<Car>을 해당 Optional<Insurance> 로 변환
            .map(optIns -> optIns.map(Insurance::getName)) // Optional<Insurance> 를 해당 이름의 Optional<String> 으로 매핑
            .flatMap(Optional::stream) // Stream<Optional<String>> 을 현재 이름을 포함하는 Stream<String> 으로 변환
            .collect(Collectors.toSet()); // 결과 문자열을 중복되지 않은 값을 갖도록 집합으로 수집
    }

    public Insurance findCheapestInsurance(Person person, Car car) {
        // 다양한 보혐회사가 제공하는 서비스 조회
        // 모든 결과 데이터 비교
        Insurance cheapestCompany = new Insurance();
        return cheapestCompany;
    }

    public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        if (person.isPresent() && car.isPresent()) {
            return Optional.of(findCheapestInsurance(person.get(), car.get()));
        }
        return Optional.empty();
    }

    public Optional<Insurance> nullSafeFindCheapestInsuranceQuiz(Optional<Person> person, Optional<Car> car) {
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    public String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge)
            .flatMap(Person::getCar)
            .flatMap(Car::getInsurance)
            .map(Insurance::getName)
            .orElse("UnKnown");
    }
}
