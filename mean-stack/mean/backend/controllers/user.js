const bcrypt = require("bcrypt"); // bcryptjs precomiled version
const jwt = require("jsonwebtoken");

const User = require("../models/user");

exports.signup = (req, res, next) => {
  bcrypt.hash(req.body.password, 10)
    .then(hash => {
      const user = new User({
        email: req.body.email,
        password: hash
      });

      user.save()
        .then(result => {
          res.status(201).json({
            message: "New user created",
            result: result
          });
        })
        .catch(error => {
          res.status(500).json({
            message: "Email is already in use",
            error
          });
        });
    })
    .catch(error => {
      res.status(500).json({
        message: "Could not register new user. Please try again later",
        error
      });
    });
}

exports.login = (req, res, next) => {
  let fetchedUser;

  User.findOne({ email: req.body.email })
    .then(user => {
      if (!user) {
        return res.status(401).json({
          message: "Authentication failed - Invalid username or password"
        });
      }

      fetchedUser = user;
      return bcrypt.compare(req.body.password, user.password);
    })
    .then(result => {
      if (!result) {
        return res.status(401).json({
          message: "Authentication failed - Invalid username or password"
        });
      }

      const token = jwt.sign(
        { email: fetchedUser.email, userId: fetchedUser._id },
        process.env.TOKEN_SECRET,
        { expiresIn: "1h" }
      );

      res.status(200).json({
        userId: fetchedUser._id,
        token,
        expiresIn: 3600 // seconds
      });
    })
    .catch(error => {
      res.status(500).json({
        message: "Authentication failed - Invalid username or password",
        error
      });
    });
}
