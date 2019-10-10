const express = require("express");

const middleware = require("../middleware/index");
const PostController = require("../controllers/post");

const router = express.Router();

// SHOW
router.get("", PostController.findAll);

router.get("/:postId", PostController.findOne);

// CREATE
router.post(
  "",
  middleware.checkToken,
  middleware.checkMime,
  PostController.create
);

// UPDATE
router.put(
  "/:postId",
  middleware.checkToken,
  middleware.checkMime,
  PostController.update
);

// DESTROY
router.delete("/:postId", middleware.checkToken, PostController.delete);

module.exports = router;
