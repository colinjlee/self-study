const mongoose = require("mongoose");
const Campground = require("./campground");
const passportLocalMongoose = require("passport-local-mongoose");

const userSchema = new mongoose.Schema({
    username: String,
    password: String,
    avatar: {
        type: String,
        default: "https://images.unsplash.com/photo-1552084117-56a987666449"
    },
    bio: String,
    firstName: String,
    lastName: String,
    resetPasswordToken: String,
    resetPasswordExpires: Date,
    email: {
        type: String,
        unique: true
    },
    isAdmin: {
        type: Boolean,
        default: false
    },
    favorites: [
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: "Campground"
        }
    ]
});

userSchema.plugin(passportLocalMongoose);

module.exports = mongoose.model("User", userSchema);