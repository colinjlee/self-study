const Post = require("../models/post");

exports.findAll = (req, res, next) => {
  const pageSize = Number(req.query.pagesize);
  const currPage = Number(req.query.page);
  let fetchedPosts;
  const postQuery = Post.find();

  if (pageSize && currPage) {
    postQuery.skip(pageSize * (currPage - 1)).limit(pageSize);
  }

  postQuery
    .then(posts => {
      fetchedPosts = posts;
      return Post.countDocuments();
    })
    .then(count => {
      res.status(200).json({
        message: "Successfully got posts",
        posts: fetchedPosts,
        numPosts: count
      });
    })
    .catch(error => {
      res.status(500).json({
        message: "Could not get posts",
        error
      })
    });
}

exports.findOne = (req, res, next) => {
  Post.findById(req.params.postId)
    .then(result => {
      res.status(200).json(result);
    })
    .catch(error => {
      res.status(500).json({
        message: "Could not find post",
        error
      })
    });
}

exports.create = (req, res, next) => {
  const url = `${req.protocol}://${req.get("host")}`;
  const post = new Post({
    title: req.body.title,
    content: req.body.content,
    imagePath: `${url}/images/${req.file.filename}`,
    author: req.userData.id
  });

  post.save()
    .then(result => {
      res.status(201).json({
        message: "Successfully added post",
        post
      });
    })
    .catch(error => {
      res.status(500).json({
        message: "Could not save new post",
        error
      })
    });
}

exports.update = (req, res, next) => {
  let imagePath = req.body.imagePath;

  if (req.file) {
    const url = `${req.protocol}://${req.get("host")}`;
    imagePath = `${url}/images/${req.file.filename}`;
  }
  const post = {
    title: req.body.title,
    content: req.body.content,
    imagePath: imagePath,
    author: req.userData.id
  };

  Post.updateOne({ _id: req.params.postId, author: req.userData.id }, post)
    .then(result => {
      console.log(result);
      if (result.n > 0) {
        res.status(200).json({
          message: `Updated post with id (${req.params.postId})`,
          post: {
            id: result._id,
            title: result.title,
            content: result.content,
            imagePath: result.imagePath
          }
        });
      } else {
        res.status(401).json({ message: "Not authorized to edit this post" });
      }
    })
    .catch(error => {
      res.status(500).json({
        message: "Could not update post",
        error
      })
    });
}

exports.delete = (req, res, next) => {
  Post.deleteOne({ _id: req.params.postId, author: req.userData.id })
    .then(result => {
      console.log(result);
      if (result.n > 0) {
        res.status(200).json({ message: `Post with id (${req.params.postId}) was deleted` });
      } else {
        res.status(401).json({ message: "Not authorized to delete this post" });
      }
    })
    .catch(error => {
      res.status(500).json({
        message: "Could not delete post",
        error
      })
    });
}
