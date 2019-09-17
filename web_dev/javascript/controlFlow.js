// Primitives: Number, String, Boolean, Null, Undefined
var num1 = 5;
var num2 = 5.5;
var num3 = -50;

var string1 = "hello";
var string2 = 'hi';

var bool1 = true;
var bool2 = false;

var empty1 = null;
var empty2;         // Undefined

// Built in methods
typeOf();        // returns type of parameter
clear();         // clears console
console.log();   // logs in console
alert();         // pop up alert on page
prompt();        // pop up prompt on page

/*
    Comparison Operators
    >       greater than
    >=      greater than or equal to
    <       less than
    <=      less than or equal to
    ==      equal value
    !=      unequal value
    ===     equal value and type
    !==     unequal value and unequal type
*/

/*
    Logical Operators
    &&      AND
    ||      OR
    !       NOT
*/

/*
    Falsey Values:
        false
        0
        ""
        null
        undefined
        Nan
    Truthy Values:
        Everything else
*/

// Conditionals: If, else, ternary, switch
let age = 17;
if (age < 18) {
    console.log("Younger than 18");
} else if (age < 21) {
    console.log("Between 18 and 21");
} else {
    console.log("Older than or equal to 21");
}

(age < 18) ? console.log("You're too young") : console.log("You're 18 or older");

switch (age) {
    case 13:
        console.log("You are a teen");
        break;
    case 18:
        console.log("You're 18!");
        break;
    case 21:
        console.log("You're 21");
        break;
    default:
        console.log("You are not 13, 18, or 21")
}

// Loops: for, for of, for each, while, do while
// Prints 0, 1, 2, 3, 4
for (let i = 0; i < 5; i++) {
    console.log(i);
}

// Prints 1, 2, 3, 4, 5
let iterableObject = [1, 2, 3, 4, 5];
for (let value of iterableObject) {
    console.log(value);
}

// Prints 1, 2, 3, 4, 5
iterableObject.forEach(function(e) {
    console.log(e);
});

// Prints 0, 1, 2, 3, 4
var n = 0;
while (n < 5) {
    console.log(n++);
}

// Prints 5, 6, 7, 8, 9
do {
    console.log(n++);
} while (n < 10);