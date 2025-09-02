// src/app/themes/themes.component.ts
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { ThemeDto } from '../../models/ThemeDto';
import { SubscriptionDto } from '../../models/SubscriptionDto';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  themes: ThemeDto[] = [];
  loading = false;
  errorMsg = '';
  asc = true;

  /** themeId -> subscriptionId (DELETE attend l'id de la souscription) */
  private subByTheme = new Map<number, number>();

  /** pour désactiver les boutons pendant l'appel réseau (optionnel) */
  busyThemeIds = new Set<number>();

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.loadThemes();
    this.loadMySubscriptions();
  }

  // ------- Data loading -------
  loadThemes(): void {
    this.loading = true;
    this.errorMsg = '';
    this.api.getAllThemes().subscribe({
      next: (data) => {
        this.themes = this.sortList(data, this.asc);
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Impossible de charger les thèmes.';
        this.loading = false;
      },
    });
  }

  loadMySubscriptions(): void {
    this.api.getMySubscriptions().subscribe({
      next: (subs: SubscriptionDto[]) => {
        this.subByTheme.clear();
        subs.forEach((s) => {
          if (typeof s.themeId === 'number' && typeof s.id === 'number') {
            this.subByTheme.set(s.themeId, s.id);
          }
        });
      },
      error: (err) => {
        console.error(err);
        // pas bloquant pour l'affichage des thèmes
      },
    });
  }

  // ------- Tri -------
  toggleOrder(): void {
    this.asc = !this.asc;
    this.themes = this.sortList(this.themes, this.asc);
  }

  private sortList(list: ThemeDto[], asc: boolean): ThemeDto[] {
    return [...list].sort((a, b) => {
      const A = (a?.title ?? '').toLowerCase();
      const B = (b?.title ?? '').toLowerCase();
      return asc ? A.localeCompare(B) : B.localeCompare(A);
    });
  }

  // ------- Abonnements -------
  isSubscribedTo(theme: ThemeDto): boolean {
    return !!theme?.id && this.subByTheme.has(theme.id);
  }

  subscribeTo(theme: ThemeDto): void {
    if (!theme?.id || this.busyThemeIds.has(theme.id)) return;
    this.busyThemeIds.add(theme.id);

    this.api.addThemeSubscription(theme.id).subscribe({
      next: (created: SubscriptionDto) => {
        if (typeof created.themeId === 'number' && typeof created.id === 'number') {
          this.subByTheme.set(created.themeId, created.id);
        }
        this.busyThemeIds.delete(theme.id);
      },
      error: (err) => {
        console.error(err);
        this.busyThemeIds.delete(theme.id);
      },
    });
  }

  unsubscribeFrom(theme: ThemeDto): void {
    if (!theme?.id || this.busyThemeIds.has(theme.id)) return;

    const subId = this.subByTheme.get(theme.id);
    if (!subId) return;

    this.busyThemeIds.add(theme.id);
    this.api.removeSubscription(subId).subscribe({
      next: () => {
        this.subByTheme.delete(theme.id);
        this.busyThemeIds.delete(theme.id);
      },
      error: (err) => {
        console.error(err);
        this.busyThemeIds.delete(theme.id);
      },
    });
  }

  // ------- *ngFor perf -------
  trackByThemeId = (_: number, t: ThemeDto) => t.id;
}
