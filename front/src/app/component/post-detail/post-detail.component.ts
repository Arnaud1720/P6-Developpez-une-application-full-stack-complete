import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import {AsyncPipe, DatePipe, NgIf, NgFor, SlicePipe} from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {ApiService} from "../../services/api.service";
import {PostDto} from "../../models/PostDto";
import {CommentaireDto} from "../../models/CommentaireDto";
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
  imports: [
    RouterLink, AsyncPipe, DatePipe, NgIf, NgFor,
    ReactiveFormsModule,
    MatCardModule, MatButtonModule, MatIconModule, MatInputModule, MatDividerModule, MatProgressSpinnerModule, SlicePipe
  ],
})
export class PostDetailComponent {
  private route = inject(ActivatedRoute);
  private api = inject(ApiService);
  private fb = inject(FormBuilder);

  postId = Number(this.route.snapshot.paramMap.get('id'));
  post?: PostDto;

  comments: CommentaireDto[] = [];
  loading = true;
  sending = false;
  errorMsg = '';

  commentForm = this.fb.nonNullable.group({
    contenu: ['', [Validators.required, Validators.minLength(2)]],
  });

  ngOnInit(): void {
    this.loading = true;

    forkJoin({
      post: this.api.getPost(this.postId),            // Observable<PostDto>
      comments: this.api.getComments(this.postId),    // Observable<CommentaireDto[]>
    }).subscribe({
      next: ({ post, comments }) => {
        this.post = post;
        this.comments = comments;
      },
      error: () => (this.errorMsg = 'Impossible de charger les données'),
      complete: () => (this.loading = false),
    });
  }

  sendComment() {
    if (this.commentForm.invalid || this.sending) return;
    this.sending = true;
    const contenu = this.commentForm.value.contenu!;
    this.api.addComment(this.postId, contenu).subscribe({
      next: (created) => {
        // ajoute en tête (ou reload liste si tu préfères)
        this.comments.unshift(created);
        this.commentForm.reset();
      },
      error: () => this.errorMsg = 'Échec de l’envoi du commentaire',
      complete: () => this.sending = false,
    });
  }
}
