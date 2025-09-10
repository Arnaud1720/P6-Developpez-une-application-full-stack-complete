import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {UserDto} from "../models/UserDto";
import {LoginResponse} from "../models/LoginResponse";
import {PostDto} from "../models/PostDto";
import {ProfilDto} from "../models/ProfilDto";
import {SubscriptionDto} from "../models/SubscriptionDto";
import {CommentaireDto} from "../models/CommentaireDto";
import {ThemeDto} from "../models/ThemeDto";
import {UpdateProfileDto} from "../models/UpdateProfileDto";


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private readonly TOKEN_KEY = 'jwt';

  private readonly baseUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) {

  }


  login(usernameOrEmail: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.baseUrl}/auth/login`,  // ← note le “/auth”
      { usernameOrEmail, password }
    );
  }

  /** Récupère les infos du profil connecté */
  myProfil(): Observable<ProfilDto> {

    return this.http.get<ProfilDto>(`${this.baseUrl}/user/me/profile`);
  }

  getAllPosts(): Observable<PostDto[]> {
    return this.http.get<PostDto[]>(`${this.baseUrl}/post/all`);
  }

  getAllThemes(): Observable<ThemeDto[]> {
    return this.http.get<ThemeDto[]>(`${this.baseUrl}/theme/all`);
  }

  // ----- User -----
  createUser(user: UserDto): Observable<UserDto> {
    return this.http.post<UserDto>(`${this.baseUrl}/user/save`, user);
  }

  getPostsOrderBy(asc: boolean): Observable<PostDto[]> {
    return this.http.get<PostDto[]>(
      `${this.baseUrl}/post/orderBy`,
      { params: { asc: String(asc) } }
    );
  }

  createPost(postDto: PostDto): Observable<PostDto> {
    return this.http.post<PostDto>(`${this.baseUrl}/post/create`, postDto);
  }

  /** Récupère le token stocké, ou null */
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  /** Stocke le token JWT */
  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  /** Supprime le token */
  clearToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
  /** Met à jour le profil avec UpdateProfileDto */
  updateProfil(dto: UpdateProfileDto): Observable<UserDto> {
    return this.http.put<UserDto>(
      `${this.baseUrl}/user/update/profil`,
      dto
    );
  }
  addSubscription(subscriptionDto: SubscriptionDto): Observable<SubscriptionDto> {
    return this.http.post<SubscriptionDto>(
      `${this.baseUrl}/subscriptions/subscribe`,
      subscriptionDto
    );
  }
  // /** Récupère toutes les souscriptions de l'utilisateur courant */
  // getMySubscriptions(): Observable<SubscriptionDto[]> {
  //   return this.http.get<SubscriptionDto[]>(`${this.baseUrl}/subscriptions/me`);
  // }

  /** Indique si un JWT valide est présent */
  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      // payload.exp est en secondes
      return payload.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }


  getPost(id: number) {
    return this.http.get<PostDto>(`${this.baseUrl}/post/${id}`); // <-- baseUrl
  }

  getComments(postId: number) {
    return this.http.get<CommentaireDto[]>(
      `${this.baseUrl}/post/${postId}/comments`
    );
  }

  addComment(postId: number, contenu: string) {
    return this.http.post<CommentaireDto>(
      `${this.baseUrl}/post/${postId}/comments`,
      { contenu }
    );
  }



  addThemeSubscription(themeId: number) {
    return this.http.post<SubscriptionDto>(`${this.baseUrl}/subscriptions/subscribe`, {
      themeId,  // <--- IMPORTANT
    });
  }

  removeSubscription(subscriptionId: number) {
    return this.http.delete<void>(`${this.baseUrl}/subscriptions/${subscriptionId}`);
  }

  getMySubscriptions() {
    return this.http.get<SubscriptionDto[]>(`${this.baseUrl}/subscriptions/me`);
  }


}
