const mongoose = require("mongoose"),
      Campground = require("./models/campground");
      Comment = require("./models/comment");

let campgroundSeeds = [
    {
        name: "Cloud's Rest",
        image: "https://farm4.staticflickr.com/3795/10131087094_c1c0a1c859.jpg",
        description: "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
        author: {
            id: "5d890ae8f8af06100ce6b8a3",
            username: "test"
        }
    },
    {
        name: "Desert Mesa",
        image: "https://farm6.staticflickr.com/5487/11519019346_f66401b6c1.jpg",
        description: "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
        author: {
            id: "5d890ae8f8af06100ce6b8a3",
            username: "test"
        }
    },
    {
        name: "Canyon Floor",
        image: "https://farm1.staticflickr.com/189/493046463_841a18169e.jpg",
        description: "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
        author: {
            id: "5d890ae8f8af06100ce6b8a3",
            username: "test"
        }
    }
]

async function seedDB() {
    // Remove all campgrounds and comments
    await Campground.deleteMany({});
    console.log("==================================");
    console.log("Removed all campgrounds");
    await Comment.deleteMany({});
    console.log("Removed all comments");

    let i = 1;
    console.log("==================================");
    console.log(">> Adding seed data...");
    // Add campground seed data
    for (const seed of campgroundSeeds) {
        let campground = await Campground.create(seed);
        
        let comment = await Comment.create({
            text: "This place is great, but I wish there was internet",
            author: "Homer"
        });
        
        // Add comment to campground comments and save campground
        campground.comments.push(comment);
        campground.save();
        console.log(`Added campground seed data: ${i++}/${campgroundSeeds.length}`);
    }
    console.log("Finished adding seed data");
    console.log("==================================");
}

module.exports = seedDB;