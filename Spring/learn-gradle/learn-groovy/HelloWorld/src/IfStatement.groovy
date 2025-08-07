def number = 10

if (number > 0) {
    println 'Positive number'
} else if (number < 0) {
    println 'Negative number'
} else {
    println 'Zero'
}

def list = [1, 2, 3]

if (list) {  // This checks if the list is non-empty
    println 'List is not empty'
} else {
    println 'List is empty'
}
