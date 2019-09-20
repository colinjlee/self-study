const express = require("express");
const app = express();
const bodyParser = require("body-parser");

app.use(express.static("public"));
app.use(bodyParser.urlencoded({extended: true}));
app.set("view engine", "ejs");

let friends = ["John", "Jane", "Josh", "Joe", "Jade"];

app.get("/", function(req, res) {
    res.render("home");
});

app.get("/love/:thing", function(req, res) {
    let thing = req.params.thing;
    res.render("love", {
        thing: thing
    });
});

app.get("/posts", function(req, res) {
    let posts = [
        {
            title: "Post 1",
            author: "John Doe"
        },
        {
            title: "Post 2",
            author: "Jane Doe"
        },
        {
            title: "Post 3",
            author: "Josh Doe"
        }
    ];

    res.render("posts", {
        posts: posts
    });
});

app.get("/friends", function(req, res) {
    res.render("friends", {
        friends: friends
    });
});

app.post("/addfriend", function(req, res) {
    let friend = req.body.name;
    friends.push(friend);

    res.redirect("/friends");
})

app.get("/speak/:animal", function(req, res) {
    let sounds = {
        dog: "woof",
        cat: "meow",
        cow: "moo",
        snake: "sss",
        pig: "oink",
        fish: "blub"
    }

    let animal = req.params.animal.toLowerCase();
    let sound = sounds[animal] ? sounds[animal] : "...";

    res.send("The " + animal + " says " + "\"" + sound + "\"");
})

app.get("/repeat/:word/:amt", function(req, res) {
    let word = req.params.word;
    let amt = Number(req.params.amt);
    let repeatedWords = [];

    if (isNaN(amt)) {
        res.send("Not a number");
    } else {
        for (let i = 0; i < amt; i++) {
            repeatedWords.push(word);
        }
        res.send(repeatedWords.join(" "));
    }
})

// Universal matching, order matters, must be at end to catch bad paths
app.get("*", function(req, res) {
    res.send("Page not found");
});

app.listen(3000, function() {
    console.log("Server has started");
})