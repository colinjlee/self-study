var cat = require("cat-me");
var knock = require("knock-knock-jokes");
var faker = require("faker");

// console.log(cat());
// console.log(knock());

console.log("////////////////////");
console.log("WELCOME TO THE SHOP");
console.log("////////////////////");

for (let i = 0; i < 10; i++) {
    console.log(faker.commerce.productName() + " - $" + faker.commerce.price());
}