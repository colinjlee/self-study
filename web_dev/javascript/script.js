// var firstName = prompt("What's your first name?");
// var lastName = prompt("What's your last name?");
// var age = prompt("What's your age?");
// var days = age * 365;

// console.log("Your full name is " + firstName + " " + lastName);
// console.log("You are " + age + " years old, or about " + days + " days old")


///////////////////////////////////////////////////////////////////

// var ans = prompt("Are we there yet?").toLowerCase();

// while (ans.indexOf("yes") === -1) {
//     ans = prompt("Are we there yet?").toLowerCase();
// }

// alert("We're here!");

///////////////////////////////////////////////////////////////////

var todos = [];
var input = prompt("What would you like to do?");

while (input !== "quit") {
    switch (input) {
        case "add":
            var todo = prompt("Enter new todo");
            todos.push(todo);
            console.log(todo + " added to list");
            break;
        case "delete":
            var index = Number(prompt("Enter an index"));

            if (index < todos.length) {
                var value = todos[index];

                todos = todos.filter(function(e, i) {
                    return index !== i;
                });
                console.log(value + " deleted from list");
            } else {
                console.log("Invalid index");
            }
            break;
        case "list":
            console.log("****************");

            if (todos.length === 0) {
                console.log("List is empty");
            } else {
                todos.forEach(function(e, i) {
                    console.log(i + ": " + e);
                });
            }

            console.log("****************");
            break;
        default:
            console.log("Invalid input");
    }

    input = prompt("What would you like to do?");
}