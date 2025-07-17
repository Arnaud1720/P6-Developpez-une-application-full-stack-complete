import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SubjectDto} from "../models/SubjectDto";
import {UserDto} from "../models/UserDto";
import {LoginResponse} from "../models/LoginResponse";
import {PostDto} from "../models/PostDto";


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private readonly TOKEN_KEY = 'jwt';

  private readonly baseUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) {

  }

  // ----- Topics -----
  getSubjects(): Observable<SubjectDto[]> {
    return this.http.get<SubjectDto[]>(`${this.baseUrl}/subjects`);
  }

  login(usernameOrEmail: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.baseUrl}/auth/login`,  // ← note le “/auth”
      { usernameOrEmail, password }
    );
  }

  getAllPosts(): Observable<PostDto[]> {
    return this.http.get<PostDto[]>(`${this.baseUrl}/post/all`);
  }

  // ----- User -----
  createUser(user: UserDto): Observable<UserDto> {
    return this.http.post<UserDto>(`${this.baseUrl}/user/save`, user);
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
}
