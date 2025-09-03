import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { PostDto } from '../../models/PostDto';
import { SubscriptionDto } from '../../models/SubscriptionDto';
import { ProfilDto } from '../../models/ProfilDto';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  posts: PostDto[] = [];
  loading = false;
  errorMsg = '';
  asc = false;
  currentUser?: ProfilDto;
  subscriptions: SubscriptionDto[] = [];

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.loading = true;
    forkJoin({
      posts: this.api.getPostsOrderBy(this.asc),
      profile: this.api.myProfil(),
      subscriptions: this.api.getMySubscriptions()
    }).subscribe({
      next: ({ posts, profile, subscriptions }) => {
        this.posts = posts;
        this.currentUser = profile;
        this.subscriptions = subscriptions;
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Impossible de charger les données';
        this.loading = false;
      }
    });
  }

  toggleOrder() {
    this.asc = !this.asc;
    this.refreshPosts();
  }

  private refreshPosts() {
    this.loading = true;
    this.api.getPostsOrderBy(this.asc).subscribe({
      next: data => {
        this.posts = data;
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Impossible de charger les articles';
        this.loading = false;
      }
    });
  }


  /**
   * Abonne l'utilisateur à ce post.
   */
  subscribeTo(post: PostDto) {
    if (!this.currentUser) return;
    const dto: SubscriptionDto = {
      userId: this.currentUser.id,
      id: undefined,
      subscribedAt: undefined,
      unsubscribedAt: undefined
    };
    this.api.addSubscription(dto).subscribe({
      next: () => this.reloadSubscriptions(),  // Recharge la liste pour synchro instantanée
      error: err => {
        console.error('Erreur abonnement', err);
      }
    });
  }


  /**
   * Recharge toutes les subscriptions (pour forcer la synchro bouton)
   */
  reloadSubscriptions() {
    this.api.getMySubscriptions().subscribe(subs => {
      this.subscriptions = subs;
      // console.log('Subscriptions à jour :', subs);
    });
  }
}
