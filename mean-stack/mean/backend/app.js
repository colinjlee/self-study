const express = require("express"),
      bodyParser = require("body-parser"),
      path = require("path"),
      mongoose = require("mongoose"),
      dotenv = require("dotenv").config({ path:  "./backend/.env"}),
      app     = express();

// Routes
const postRoutes = require("./routes/posts");
const userRoutes = require("./routes/users");

// Configs
app.use(bodyParser.json());
app.use("/images", express.static(path.join("backend/images")));

mongoose.connect(process.env.MONGODB_URL, {
    useNewUrlParser: true,
    useCreateIndex: true,
    useUnifiedTopology: true,
    useFindAndModify: false
  }).then(() => {
    console.log("Connected to mongoose");
  })
  .catch(() => {
    console.log("Error connecting to mongoose");
  });

app.use((req, res, next) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  );
  res.setHeader(
    "Access-Control-Allow-Methods",
    "GET, POST, PATCH, PUT, DELETE, OPTIONS"
  );

  next();
});

app.use("/api/posts", postRoutes);
app.use("/api/users", userRoutes);

module.exports = app;
