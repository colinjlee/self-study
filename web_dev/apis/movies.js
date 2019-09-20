// http://www.omdbapi.com/?s=guardians+of+the+galaxy&apikey=thewdb
// http://www.omdbapi.com/?i=tt3896198&apikey=thewdb

const express = require("express");
const request = require("request");
const rp = require("request-promise")
const app = express();

app.set("view engine", "ejs");

app.get("/", (req, res) => {
    res.render("search");
});

app.get("/results", (req, res) => {
    let movie = req.query.search;
    let url = `http://www.omdbapi.com/?s=${movie}&apikey=thewdb`;
    let details = {};

    rp(url)
        .then(body => {
            const data = JSON.parse(body);
            let promises = [];

            data["Search"].forEach(movie => {
                let id = movie["imdbID"];
                let url = `http://www.omdbapi.com/?i=${id}&apikey=thewdb`;

                promises.push(rp(url).then(body => {
                    details[id] = JSON.parse(body);
                }));
            });

            Promise.all(promises)
                .then(() => {
                    res.render("results", {
                        data: data,
                        details: details
                    })
                });
        })
        .catch(error => {
            console.log(error);
        })
});

app.listen(3000, () => {
    console.log("Movies.js started");
});