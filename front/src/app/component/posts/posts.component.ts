import { Component, HostListener, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { PostDto } from '../../models/PostDto';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  posts: PostDto[] = [];
  loading = false;
  errorMsg = '';
  cols = 2;

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.onResize();
    this.fetchPosts();
  }

  @HostListener('window:resize')
  onResize() {
    const w = window.innerWidth;
    // < 600px → 1 colonne, < 960px → 2, sinon 3
    this.cols = w < 600 ? 1 : w < 960 ? 2 : 3;
  }

  fetchPosts() {
    this.loading = true;
    this.api.getAllPosts().subscribe({
      next: (data) => {
        this.posts = data;
        this.loading = false;
      },
      error: (err) => {
        this.errorMsg = 'Impossible de charger les articles';
        this.loading = false;
      }
    });
  }
}
