var listItems = $("li");
var styles = {
    color: "red",
    border: "1px solid black"
}

listItems.css("background-color", "blue");
listItems.css(styles);

var highlight = $(".highlight");

$("div").css("background-color", "purple");
$("div").first().css("color", "pink");
// $("div:first")
// $("div:first-of-type")
$(".highlight").css("width", "200px");
$("#third").css("border", "1px solid orange");

console.log("Old header: " + $("h1").text());
$("h1").text("Changed header");

// $("ul").html("<li>Changed list item</li>");

$("img").css("width", "250px");
$("img").attr("src", "https://images.unsplash.com/photo-1568666476368-72bfb8869817?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=80");

console.log("Input text: " + $("input").val());
$("input").val("Changed input text");
console.log("Selection: " + $("select").val());

$("h1").addClass("class1");
$("h1").addClass("class2");

$("h1").removeClass("class2");

$("h1").toggleClass("class2");

// Events
$("button").click(function() {
    $("li").css("background-color", "yellow");
});

$("#button2").click(function() {
    $("li").css("font-size", "30px");
});

$("input").keypress(function(event) {
    if (event.which === 13) {
        console.log("You hit enter");
    }

    console.log($(this).val());
});

$("li").on("click", function() {
    $(this).css("background-color", "gray");
});

$("li").on("mouseenter", function() {
    $(this).css("background-color", "yellow");
});

$("li").on("mouseleave", function() {
    $(this).css("background-color", "coral");
});

// Animations
$("#fadeIn").on("click", function() {
    $("li").fadeIn();
});

$("#fadeOut").on("click", function() {
    $("li").fadeOut(2000, function() {
        console.log("Done fading out");
    });
});

$("#fadeToggle").on("click", function () {
    $("li").fadeToggle();
});

$("#slideToggle").on("click", function () {
    $("li").slideToggle();
});