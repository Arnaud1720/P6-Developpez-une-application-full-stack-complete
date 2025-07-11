import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {PostDto} from "../models/PostDto";
import {SubjectDto} from "../models/SubjectDto";
import {SubscriptionDto} from "../models/SubscriptionDto";
import {UserDto} from "../models/UserDto";
import {LoginResponse} from "../models/LoginResponse";


@Injectable({
  providedIn: 'root'
})
export class ApiService {


  private readonly baseUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) {}

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

  // ----- User -----
  createUser(user: UserDto): Observable<UserDto> {
    return this.http.post<UserDto>(`${this.baseUrl}/user/save`, user);
  }

}
