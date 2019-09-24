// Libraries
const express           = require("express"),
      bodyParser        = require("body-parser"),
      mongoose          = require("mongoose"),
      flash             = require("connect-flash"),
      passport          = require("passport"),
      LocalStrategy     = require("passport-local"),
      methodOverride    = require("method-override"),
      app               = express();

// Schemas
const Campground        = require("./models/campground"),
      User              = require("./models/user"),
      Comment           = require("./models/comment");

// Routes
const campgroundRoutes  = require("./routes/campgrounds"),
      commentRoutes     = require("./routes/comments"),
      authRoutes        = require("./routes/auth");

// Seed data
const seedDB = require("./seeds");

/////////////////////////////////////////////////////////////////
// Configs

// Express configs
app.set("view engine", "ejs");
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.static(__dirname + "/public"));
app.use(flash());
app.use(methodOverride("_method"));
app.use(require("express-session")({
    secret: "This is a secret",
    resave: false,
    saveUninitialized: false
}));

app.use(passport.initialize());
app.use(passport.session());
app.use((req, res, next) => {
    res.locals.currUser = req.user;
    res.locals.errorMessage = req.flash("error");
    res.locals.successMessage = req.flash("success");
    next();
});

// Use route files
app.use("/campgrounds", campgroundRoutes);
app.use("/campgrounds/:id/comments", commentRoutes);
app.use("/", authRoutes);

// Passport configs
passport.use(new LocalStrategy(User.authenticate()));
passport.serializeUser(User.serializeUser());
passport.deserializeUser(User.deserializeUser());

// Mongoose configs
mongoose.connect("mongodb://localhost:27017/yelp_camp", {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

// End configs
/////////////////////////////////////////////////////////////////

// Reset DB with seed data
// seedDB();

app.get("/", (req, res) => {
    res.locals.title = "Yelp Camp";
    res.render("landing");
});

app.listen(3000, () => {
    console.log("Yelp Camp server started");
})