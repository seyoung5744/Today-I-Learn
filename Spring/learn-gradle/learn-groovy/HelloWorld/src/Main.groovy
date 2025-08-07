//// -- Primitive Data Types
//// Byte
//byte b = 10
//println "Byte: $b"
//println b.class
//
//// Short
//short s = 30000
//println "Short: $s"
//
//// Integer
//int i = 100000
//println "Integer: $i"
//
//// Long
//long l = 10000000000L
//println "Long: $l"
//
//// Float
//float  f = 10.5f
//println "Float: $f"
//
//// Double
//double d = 30.99
//println "Double: $d"
//
//// Character
//char c = 'A'
//println "Character: $c"
//
//// Boolean
//boolean bool = true;
//println "Boolean: $bool"
//
//// -- Reference Data Types
//// String
//String str = "Hello, Groovy!"
//println "String: $str"
//
//// BigInteger
//BigInteger bigInt = new BigInteger("12345678901234567890")
//println "BigInteger: $bigInt"
//
//// BigDecimal
//BigDecimal bigDec = new BigDecimal("123456.6789")
//println "BigDecimal: $bigDec"
//
//// List
//List<Integer> list = [1, 2, 3, 4, 5]
//println "List: $list"
//
//// Map
//Map<String, Integer> map = [name: 1, age: 2]
//println "Map: $map"
//
//// Range
//Range range = 1..10
//println "Range: $range"

// -- Special Data Types
// Closure
//def sayHello = { println "Hello, Groovy!"}
//sayHello()
//
//def add = {a1, a2 -> a1 + a2}
//println add(5, 3)
//
//def greet = { name -> println "Hello, $name!"}
//greet("Groovy")
//
//// implicit parameter
//def square = { it * it }
//println square(4)
//
//def greetWithDefault = { name = "Stranger" -> println "Hello, $name"}
//greetWithDefault()
//greetWithDefault("Bod")
//
//def operate(a, b, operation) {
//    return operation(a, b)
//}
//
//def result = operate(4, 5, {x, y -> x + y})
//println result
//
//// Null and Safe Navigation Operator
//String nullableString = null
//println "Nullable String Length: ${nullableString?.length()}"

// -- Dynamic Typing with def
def dynamicVar = "I am a string"
println "Dynamic Variable: $dynamicVar (Type: ${dynamicVar.getClass().name})"

dynamicVar = 42 // Now it's an integer
println "Dynamic Variable: $dynamicVar (Type: ${dynamicVar.getClass().name})"

dynamicVar = [1, 2, 3] // Now it's a list
println "Dynamic Variable: $dynamicVar (Type: ${dynamicVar.getClass().name})"

