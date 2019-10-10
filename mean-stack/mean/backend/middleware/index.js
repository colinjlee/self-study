const jwt = require("jsonwebtoken");
const multer = require("multer");

const MIME_TYPE_MAP = {
  "image/png": "png",
  "image/jpeg": "jpg",
  "image/jpg": "jpg"
}

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    let error = new Error("Invalid mime type");

    if (MIME_TYPE_MAP[file.mimetype]) {
      error = null;
    }

    cb(error, "backend/images");
  },
  filename: (req, file, cb) => {
    const name = file.originalname.toLowerCase().split(" ").join("-");
    const extension = MIME_TYPE_MAP[file.mimetype];
    cb(null, `${name}-${Date.now()}.${extension}`);
  }
});

middlewareObj = {}

middlewareObj.checkToken = (req, res, next) => {
  try {
    const token = req.headers.authorization.split(" ")[1];
    const decodedToken = jwt.verify(token, process.env.TOKEN_SECRET);

    req.userData = {
      id: decodedToken.userId,
      email: decodedToken.email
    }
    next();
  } catch (error) {
    res.status(401).json({
      message: "Authentication failed",
      error
    })
  }
};

middlewareObj.checkMime = multer({ storage: storage }).single("image");

module.exports = middlewareObj;
