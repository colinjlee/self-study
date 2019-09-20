/*
    Keyword 'this'
    1. When not inside a declared object, refers to global window object
    2. When inside a declared object, refers to closest parent object
    3. Choose what 'this' refers to by using call, apply, or bind
        Note: Only used on methods
              First arg is always 'thisArg' - what 'this' will refer to
        Call/Apply  - Invokes function right away
        Bind        - Returns a function definition
    4. When using keyword 'new', refers to the new object
        New - 1. Creates a new object
              2. Sets 'this' to be the new object
              3. Adds implicit 'return this' - hence #2
              4. Adds dunder proto property to new object
*/

// 1
console.log(this);

// 2
var person = {
    name: "Colin",
    sayHi: function() {
        // Refers to person
        return "Hello " + this.name;
    },
    sayBye: function() {
        setTimeout(function() {
            // Refers to global object
            console.log("Bye " + this.name);
        }, 1000);
    },
    dog: {
        sayHi: function() {
            // Undefined, dog is closest parent object which has no name
            return "Hi " + this.name;
        }
    },
    addNumbers: function(a, b) {
        return this.name + " just added the numbers to " + (a + b);
    }
}

// 3
var animal = {
    name: "Mittens"
}

// call
person.sayHi();                 // Hello Colin
person.sayHi.call(animal);      // Hello Mittens

// apply
person.sayHi.apply(animal);     // Hello Mittens
person.addNumbers(1, 2);
person.addNumbers.call(animal, 1, 2);
person.addNumbers.apply(animal, [1, 2]);

// bind
var animalAdd = person.addNumbers.bind(animal, 1, 2, 3, 4);
animalAdd();

var animalAdd2 = person.addNumbers.bind(animal, 1, 2);  // Partial application
animalAdd2(3, 4);

// 4
function House(bedrooms, bathrooms, numSqft) {
    this.bedrooms = bedrooms;
    this.bathrooms = bathrooms;
    this.numSqft = numSqft;
}

// Error without keyword 'new', 'this' refers to global object
var house1 = House(3, 4, 2000);
// With keyword 'new', 'this' refers to the newly created object
var house2 = new House(2, 2, 1000);