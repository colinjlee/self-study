import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { Post } from './post.model';
import { environment } from '../../environments/environment';

// Injects a single instance throughout the app
@Injectable({ providedIn: 'root' })
export class PostService {
  private posts: Post[] = [];
  private postsUpdated = new Subject<{ posts: Post[], postCount: number }>();

  constructor(private http: HttpClient, private router: Router) {}

  getPost(postId: string) {
    return this.http.get<{
      _id: string,
      title: string,
      content: string,
      imagePath: string,
      author: string
    }>(`${environment.backendPostsUrl}${postId}`);
  }

  getPosts(pageSize: number, currPage: number) {
    const queryParams = `?pagesize=${pageSize}&page=${currPage}`;
    this.http.get<{ message: string, posts: any, numPosts: number }>(environment.backendPostsUrl + queryParams)
      .pipe(map((postData) => {
        return { posts: postData.posts.map(post => {
          return {
            id: post._id,
            title: post.title,
            content: post.content,
            imagePath: post.imagePath,
            author: post.author
          };
        }), numPosts: postData.numPosts};
      }))
      .subscribe(transformedPostData => {
        this.posts = transformedPostData.posts;
        this.postsUpdated.next({ posts: [...this.posts], postCount: transformedPostData.numPosts });
    });
  }

  getPostUpdateListener() {
    return this.postsUpdated.asObservable();
  }

  addPost(postTitle: string, postContent: string, postImage: File) {
    const postData = new FormData();
    postData.append('title', postTitle);
    postData.append('content', postContent);
    postData.append('image', postImage, postTitle);

    this.http.post<{message: string, post: Post}>(
      environment.backendPostsUrl,
      postData
      )
      .subscribe(responseData => {
        this.router.navigate(['/']);
      });
  }

  deletePost(postId: string) {
    return this.http.delete(`${environment.backendPostsUrl}${postId}`);
  }

  updatePost(postId: string, postTitle: string, postContent: string, postImage: File | string) {
    let postData: FormData | Post;

    if (typeof(postImage) === 'object') {
      postData = new FormData();
      postData.append('title', postTitle);
      postData.append('content', postContent);
      postData.append('image', postImage);
    } else {
      postData = {
        id: postId,
        title: postTitle,
        content: postContent,
        imagePath: postImage,
        author: null // Would open up to client side
      };
    }

    this.http.put(`${environment.backendPostsUrl}${postId}`, postData)
      .subscribe((response: {message: string, post: Post}) => {
        this.router.navigate(['/']);
      });
  }
}
