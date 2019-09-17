// Function declaration: Defined as soon as script is run
// i.e. loads before any code is executed due to hoisting
function sayHi() {
    console.log("Hi");
}

// Function expression: Loads only when reached line of code
var a = function() {
    console.log("Function expression");
}

function isEven(num) {
    return num % 2 === 0;
}

function factorial(num) {
    if (num <= 0) {
        return 1;
    }

    return num * factorial(num-1);
}

function kebabToSnake(word) {
    return word.replace(/-/g, "_");
}

// Higher order functions: take in or return other functions
setInterval(sayHi, 1000);

// Anonymous functions: one time functions with no name
setInterval(function() {
    console.log("Anonymous function");
}, 2000);