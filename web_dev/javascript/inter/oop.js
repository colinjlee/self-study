// Syntactic sugar for prototype based inheritance
class House {
    // Public class fields
    bedrooms = 0;
    bathrooms;
    
    // Constructor
    constructor(bedrooms, bathrooms, numSqft) {
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.numSqft = numSqft;
    }

    // Public static method
    static staticMethod() {
        return "This is a static method inside House class";
    }

    // Public method
    publicMethod() {
        return "This is a plain public method inside House class";
    }
}

/*
    Constructor functions - create objects
        - Prototype property on function is the class object
        - An instance of the class object - created with the keyword 'new'
            has __proto__ that points back to the class object
*/

// function House(bedrooms, bathrooms, numSqft) {
//     this.bedrooms = bedrooms;
//     this.bathrooms = bathrooms;
//     this.numSqft = numSqft;
// }

// Things specific to instances of vehicles
function Vehicle(make, model, year) {
    this.make = make;
    this. model = model;
    this.year = year;
    this.isRunning = false;
}

// Things belonging to and consistent with every vehicle
Vehicle.prototype.turnOn = () => {
    this.isRunning = true;
};

Vehicle.prototype.turnOff = () => {
    this.isRunning = false;
};

function Car(make, model, year) {
    // Vehicle.call(this, make, model, year);
    // Vehicle.apply(this, [make, model, year]);
    Vehicle.apply(this, arguments);
    this.numWheels = 4;  // Can also use preset values
}

