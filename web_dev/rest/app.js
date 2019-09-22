/*
    RESTful Routes
    1. Index        /dogs               GET         List all dogs
    2. New          /dogs/new           GET         Show new dog form
    3. Create       /dogs               POST        Create new dog, then redirect
    4. Show         /dogs/:id           GET         Show details of one specific dog
    5. Edit         /dogs/:id/edit      GET         Show edit form for one specific dog
    6. Update       /dogs/:id           PUT         Update one specific dog, then redirect
    7. Destroy      /dogs/:id           DELETE      Delete one specific dog, then redirect
*/

const express = require("express"),
      expressSanitizer = require("express-sanitizer"),
      bodyParser = require("body-parser"),
      mongoose = require("mongoose"),
      methodOverride = require("method-override"),
      app = express();

app.set("view engine", "ejs");
app.use(express.static("public"));
app.use(bodyParser.urlencoded({extended: true}));
app.use(expressSanitizer());
app.use(methodOverride("_method"));

mongoose.connect("mongodb://localhost:27017/restful_blog",
                {
                    useNewUrlParser: true,
                    useUnifiedTopology: true
                });

let blogSchema = new mongoose.Schema({
    title: String,
    image: String,
    body: String,
    created: {
        type: Date,
        default: Date.now
    }
});

let Blog = mongoose.model("Blog", blogSchema);

// INDEX Route
app.get("/", (req, res) => {
    res.redirect("/blogs");
});

// INDEX Route
app.get("/blogs", (req, res) => {
    Blog.find({}, (err, blogs) => {
        if (err) {
            console.log(err);
        } else {
            res.render("index", {
                blogs: blogs
            });
        }
    });
});

// NEW Route
app.get("/blogs/new", (req, res) => {
    res.render("new");
});

// CREATE Route
app.post("/blogs", (req, res) => {
    req.body.blog.body = req.sanitize(req.body.blog.body);

    Blog.create(req.body.blog, (err, blog) => {
        if (err) {
            console.log(err);
        } else {
            res.redirect("/blogs");
        }
    });
});

// SHOW Route
app.get("/blogs/:id", (req, res) => {
    Blog.findById(req.params.id, (err, blog) => {
        if (err) {
            console.log(err);
        } else {
            res.render("show", {
                blog: blog
            });
        }
    });
});

// EDIT Route
app.get("/blogs/:id/edit", (req, res) => {
    Blog.findById(req.params.id, (err, blog) => {
        if (err) {
            console.log(err);
        } else {
            res.render("edit", {
                blog: blog
            });
        }
    })
});

// UPDATE Route
app.put("/blogs/:id", (req, res) => {
    req.body.blog.body = req.sanitize(req.body.blog.body);
    
    Blog.findByIdAndUpdate(
        req.params.id,
        req.body.blog,
        (err, blog) => {
            if (err) {
                console.log(err);
            } else {
                res.redirect("/blogs/" + req.params.id);
            }
    });
});

// DESTROY Route
app.delete("/blogs/:id", (req, res) => {
    Blog.findByIdAndDelete(req.params.id, (err, blog) => {
        if (err) {
            console.log(err);
        } else {
            res.redirect("/blogs");
        }
    });
});

app.listen(3000, () => {
    console.log("Restful blog server started");
});