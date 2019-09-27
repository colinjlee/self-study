// Libraries
const express           = require("express"),
      expressSanitizer  = require("express-sanitizer"),
      bodyParser        = require("body-parser"),
      mongoose          = require("mongoose"),
      flash             = require("connect-flash"),
      passport          = require("passport"),
      LocalStrategy     = require("passport-local"),
      methodOverride    = require("method-override"),
      favicon           = require("serve-favicon"),
      dotenv            = require("dotenv").config(),
      app               = express();

// Schemas
const User              = require("./models/user");

// Routes
const campgroundRoutes  = require("./routes/campgrounds"),
      commentRoutes     = require("./routes/comments"),
      userRoutes        = require("./routes/users"),
      authRoutes        = require("./routes/auth");

// Seed data
const seedDB = require("./seeds");

/////////////////////////////////////////////////////////////////
// Configs

// Express configs
app.set("view engine", "ejs");
app.use(favicon(__dirname + "/public/images/favicon.ico"));
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.static(__dirname + "/public"));
app.use(flash());
app.use(expressSanitizer());
app.use(methodOverride("_method"));
app.use(require("express-session")({
    secret: "This is a secret",
    resave: false,
    saveUninitialized: false
}));

app.locals.moment = require("moment");

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
app.use("/users", userRoutes);
app.use("/", authRoutes);

// Passport configs
passport.use(new LocalStrategy(User.authenticate()));
passport.serializeUser(User.serializeUser());
passport.deserializeUser(User.deserializeUser());

// Mongoose configs
mongoose.connect(process.env.DATABASE_URL, {
    useNewUrlParser: true,
    useCreateIndex: true,
    useUnifiedTopology: true,
    useFindAndModify: false
}).then(() => {
    console.log("Connected to DB");
}).catch(err => {
    console.log("ERROR: " + err.message);
});

// End configs
/////////////////////////////////////////////////////////////////

// Reset DB with seed data
// seedDB();

app.get("/", (req, res) => {
    res.locals.title = "Yelp Camp";
    res.render("landing");
});

app.listen(process.env.PORT, process.env.IP, () => {
    console.log("YelpCamp server started on port " + process.env.PORT);
});