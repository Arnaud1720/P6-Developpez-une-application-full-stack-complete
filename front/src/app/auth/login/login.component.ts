import { Component, OnInit }             from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router }                        from '@angular/router';
import { MatSnackBar }                   from '@angular/material/snack-bar';
import { ApiService }                    from '../../services/api.service';
import {JwtPayload} from "../../models/JwtPayload";

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
      private snackBar: MatSnackBar,
    ) {
    }

    ngOnInit() {
      this.loginForm = this.fb.group({
        usernameOrEmail: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
      });
    }

    onSubmit() {
      if (this.loginForm.invalid) return;

      const { usernameOrEmail, password } = this.loginForm.value;
      this.api.login(usernameOrEmail, password).subscribe({
        next: resp => {
          const token = (resp as any).accessToken as string;
          console.log('🔑 Jeton reçu :', token);

          // 1) Vérifier format JWT (3 segments)
          if (token.split('.').length !== 3) {
            this.snackBar.open('Format de jeton invalide', 'Fermer', { duration: 3000 });
            return;
          }
          // 2) Vérifiez qu’il y a bien un accessToken
          if (!resp || !('accessToken' in resp)) {
            console.error('⚠️ Pas de accessToken dans la réponse !');
            return;
          }
          // 2) Décoder via jwt-decode
          let payload: JwtPayload;
          try {
            const jwt_decode = require('jwt-decode') as (token: string) => JwtPayload;
          } catch (e) {
            console.error('❌ Erreur jwt-decode', e);
            this.snackBar.open('Jeton non décodable', 'Fermer', { duration: 3000 });
            return;
          }

          // 4) Stocker + rediriger
          localStorage.setItem('jwt', token);
          this.router.navigate(['/posts']);
        },
        error: err => {
          console.error('❌ Échec login', err);
          this.snackBar.open('Identifiants invalides', 'Fermer', { duration: 3000 });
        }
      });
    }
  }



