const mongoose = require("mongoose");
const Comment = require("./comment");

const campgroundSchema = new mongoose.Schema({
    name: String,
    price: String,
    image: String,
    description: String,
    website: String,
    location: String,
    lat: Number,
    lng: Number,
    favorites: {
        type: Number,
        default: 0
    },
    date: {
        type: Date,
        default: Date.now
    },
    author: {
        id: {
            type: mongoose.Schema.Types.ObjectId,
            ref: "User"
        },
        username: String
    },
    comments: [
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: "Comment"
        }
    ]
});

// Remove comments associated with campground when deleting campground
campgroundSchema.pre("remove", async function(next) {
    try {
        await Comment.remove({
            "_id": {
                $in: this.comments
            }
        });
        next();
    } catch (err) {
        next(err);
    }
});

module.exports = mongoose.model("Campground", campgroundSchema);