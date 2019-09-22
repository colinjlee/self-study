const mongoose = require("mongoose");

mongoose.connect("mongodb://localhost:27017/data_assoc2",
    {
        useNewUrlParser: true,
        useUnifiedTopology: true
    });


// One to many association. One user can have many posts
// Need to define posts before user since it is embedded in user
const Post = require("./models/post");
const User = require("./models/user");

// let myPost = new Post({
//     title: "Post title 2",
//     content: "Post content 2"
// });

// let myUser = new User({
//     name: "John Doe",
//     email: "john@email.com"
// });

// myPost.save((err, post) => {
//     if (err) {
//         console.log(err);
//     } else {
//         console.log(post);
//     }
// });

// Post.create({
//     title: "Post title 4",
//     content: "Post content 4"
// }, (err, post) => {
//     console.log(post);
//     myUser.posts.push(post);
// });

// myUser.posts.push(myPost);

// myUser.save((err, user) => {
//     if (err) {
//         console.log(err);
//     } else {
//         console.log(user);
//     }
// });

User.findOne({name: "John Doe"}).populate("posts").exec((err, user) => {
    if (err) {
        console.log(err);
    } else {
        console.log(user);
    }
});