// Ways to make objects
var person1 = {};
person.name = "Colin";
person.age = 1;

var person2 = {
    name: "Colin2",
    age: 2
};

var person3 = new Object();
person3.name = "Colin3";
person.age = 3;

// Example
var movies = [
    {
        title: "Movie 1",
        watched: true,
        rating: 3
    },
    {
        title: "Movie 2",
        watched: false,
        rating: 4
    }
];

function buildMovieString(movie) {
    var res = "You have ";

    if (movie.watched) {
        res += " watched ";
    } else {
        res += " not watched ";
    }

    res += "\"" + movie.title + "\"";
    res += " - " + movie.rating + " stars";

    return res;
}

movies.forEach(function(movie) {
    console.log(buildMovieString(movie));
});

// Functions in objects, avoid namespace issues
var dog = {
    name: "Bones",
    age: 2,
    speak: function() {
        return "Woof!";
    }
}