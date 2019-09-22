const mongoose = require("mongoose");

mongoose.connect("mongodb://localhost:27017/data_assoc",
                {
                    useNewUrlParser: true,
                    useUnifiedTopology: true
                });
                

// One to many association. One user can have many posts
// Need to define posts before user since it is embedded in user
const postSchema = new mongoose.Schema({
    title: String,
    content: String
});

const Post = mongoose.model("Post", postSchema);

const userSchema = new mongoose.Schema({
    name: String,
    email: String,
    posts: [postSchema]
});

const User = mongoose.model("User", userSchema);

let myPost = new Post({
    title: "Post title",
    content: "Post content"
});

let myUser = new User({
    name: "John Doe",
    email: "john@email.com"
});

// myUser.posts.push({
//     title: "How to code",
//     content: "Covers javascript"
// });

// myUser.save((err, user) => {
//     if (err) {
//         console.log(err);
//     } else {
//         console.log(user);
//     }
// });

// myPost.save((err, post) => {
//     if (err) {
//         console.log(err);
//     } else {
//         console.log(post);
//     }
// });

User.findOneAndUpdate({name: "John Doe"}, 
        { $push: { posts: {
            title: "Another post",
            content: "bleh"
        }}},
        {new: true},
        (err, user) => {
            if (err) {
                console.log(err);
            } else {
                console.log(user);
            }
        }
)