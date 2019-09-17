var colors = ["red", "green", "blue"];
colors.length;          // Length of array
colors[0] = "orange";   // Change element
colors[3] = "yellow";   // Add alement
colors[10] = "purple";  // Add element with empty spaces in between

// Arrays can hold multiple types of data
var multiple = [1, 2, 3, "one", "two", null];

// Array methods
colors.push("red");         // Add new element to end
colors.pop();               // Remove last element
colors.unshift("white");    // Add new element to front
colors.shift();             // Remove first element
colors.indexOf("red");      // Get index of first instance of element or -1 if not present
colors.slice(0, 3);         // Get subarray from starting index (inclusive) to ending (exclusive)

// 2d array
var board = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
];

// Prints array in reverse order
function printReverse(arr) {
    for (let i = arr.length-1; i >= 0; i--) {
        console.log(arr[i]);
    }
}

// Checks if all elements in array are the same
function isUniform(arr) {
    if (arr.length > 0) {
        var elt = arr[0];

        for (let i = 1; i < arr.length; i++) {
            if (arr[i] !== elt) {
                return false;
            }
        }

        return true;
    }

    return true;
}

// Sums array of numbers
function sumArray(arr) {
    var sum = 0;
    arr.forEach(function(e) {
        sum += e;
    });

    return sum;
}

// Find max in array of numbers
function findMax(arr) {
    var max = arr[0];

    for (let i = 1; i < arr.length; i++) {
        max = arr[i] > max ? arr[i] : max;
    }

    return max;
}

// for each
function myForEach(arr, func) {
    for (let i = 0; i < arr.length; i++) {
        func(arr[i]);
    }
}

Array.prototype.myForEach = function(func) {
    for (let i = 0; i < this.length; i++) {
        func(this[i]);
    }
}