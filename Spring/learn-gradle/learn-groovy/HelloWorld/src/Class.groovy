class Person {
    String name
    int age

    // override
    def getName() {
        "My name is ${name}"
    }

    // Method to display person details
    def showDetails() {
        println "Name: $name, Age: $age"
    }
}

p = new Person(name:'Joon', age: 40)
p.showDetails()

// Those are coming from setter or getter
println(p.name)

// -- Constructor
class Car {
    String model
    int year

    // Custom constructor
    Car(String model, int year) {
        this.model = model
        this.year = year
    }
}

def car = new Car("Tesla", 2024)
println "Model: ${car.model}, Year: ${car.year}"  // Output: Model: Tesla, Year: 2020

// -- Inheritance
class Animal {
    def speak() {
        println "Animal sound"
    }
}

class Dog extends Animal {
    @Override
    def speak() {
        println "Bark"
    }
}

def dog = new Dog()
dog.speak()  // Output: Bark

// -- Static Methods and Properties
class Calculator {
    static double PI = 3.14159

    static double square(double num) {
        return num * num
    }
}

println Calculator.PI           // Output: 3.14159
println Calculator.square(5)    // Output: 25.0
