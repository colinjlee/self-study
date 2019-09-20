/*
    - Closures exist when an inner function uses variables declared
        in an outer function
    - They do not exist if an inner function is not returned
        nor when the inner function doesn't use variables from outer function
    - Can use closures to make private variables
*/

function outer() {
    var data = "closures are ";
    return function inner() {
        var innerData = "awesome";
        return data + innerData;
    }
}

console.log(outer());        // Returns inner function
console.log(outer()());      // Returns string "closures are awesome"

function adder(num) {
    return function(num2) {
        return num + num2;
    }
}

var add5 = adder(5);

console.log(add5);          // Returns function that adds 5 to antoher number
console.log(add5(2));       // Returns 7

// Private variables
function counter() {
    var count = 0;
    return () => {
        return ++count;
    }
}

var c = counter();          // Function that counts
console.log(c());           // 1
console.log(c());           // 2
console.log(c());           // 3

var c2 = counter();
console.log(c2());          // 1
console.log(c2());          // 2

console.log(c());           // 4