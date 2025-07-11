import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ApiService} from "../../services/api.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  hide = true;

  constructor(
    private fb: FormBuilder,
    private api: ApiService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
  }

  ngOnInit() {
    this.loginForm = this.fb.group({
      usernameOrEmail: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit() {
    console.log(' onSubmit déclenché');    // ← suis bien que ça passe ici
    if (this.loginForm.invalid) return;

    const {usernameOrEmail, password} = this.loginForm.value;
    this.api.login(usernameOrEmail, password).subscribe({
      next: resp => {
        const token = resp.accessToken;
        console.log('JWT raw:', token);
        // 2) décode le payload (partie centrale du JWT)
        const payloadJson = window.atob(token.split('.')[1]);
        const payload = JSON.parse(payloadJson);
        console.log('JWT payload:', payload);

        // 3) vérifie la date d’expiration (champ exp en secondes)
        const isExpired = payload.exp * 1000 < Date.now();
        console.log('Token expiré ?', isExpired);

        // si tout est OK, stocke et redirige
        if (!isExpired) {
          localStorage.setItem('jwt', token);
          this.router.navigate(['/']);
        } else {
          console.warn('Le token est déjà expiré !');
        }
      },
      error: err => {
        console.error('Échec login', err);
      }
    });

  }
}
