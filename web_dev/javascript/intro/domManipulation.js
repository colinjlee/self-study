/*
    Document Selector Methods:
        document.getElementById()
        document.getElementsByClassName()
        document.getElementByTagName()
        document.querySelector()
        document.querySelectorAll()
*/
var byID = document.getElementById("highlight");

// Note the return type of HTMLCollection
var byClass = document.getElementsByClassName("bolded");

var byTag = document.getElementsByTagName("li");

// Returns first element that matches given CSS style selector
var byQuery = document.querySelector("#highlight");

// Returns all elements that match
var byQueryAll = document.querySelectorAll(".bolded");

//////////////////////////////////////////////////////////////////

byQuery.style.background = "yellow";
byQueryAll.forEach(e => e.style.fontWeight = "bold");

var paragraph = document.querySelector("p");
// Text only
console.log("Text content: " + paragraph.textContent);
// Includes inner html tags
console.log("Inner HTML:   " + paragraph.innerHTML);
// Includes own html tags
console.log("Outer HTML:   " + paragraph.outerHTML);

var link = document.querySelector("a");
console.log(link.textContent);
console.log(link.getAttribute("href"));
link.setAttribute("href", "http://www.reddit.com");
link.textContent = "Reddit Link";
console.log(link.textContent);
console.log(link.getAttribute("href"));


var h1 = document.querySelector("h1");
h1.style.color = "white";

var body = document.querySelector("body");
var isBlack = false;

setInterval(function() {
    if (isBlack) {
        body.style.background = "white";
    } else {
        body.style.background = "black";
    }
    isBlack = !isBlack;
}, 2000);

//////////////////////////////////////////////////////////////////

/*
    Separation of Concerns - In general should separate
        - Structure         (html)
        - Style             (css)
        - Logic/Behavior    (js)
*/
var btn = document.querySelector("button");
btn.classList.add("big");
// btn.classList.remove("big");
// btn.classList.toggle("big");

btn.onclick = function() {
    btn.classList.toggle("blue");
};

// btn.addEventListener("click", function() {
//     btn.style.color = "yellow";
// });