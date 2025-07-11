import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserDto} from "../../models/UserDto";
import {ApiService} from "../../services/api.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
   registerForm!: FormGroup;
   hide = true;

  constructor(  private api: ApiService,
                private router: Router,
                private snackBar: MatSnackBar,
                private fb: FormBuilder) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    })
  }
  onSubmit() {
    if (!this.registerForm.valid) {
      return;
    }

    // 1) Récupère les valeurs du formulaire
    const newUser: UserDto = this.registerForm.value;

    // 2) Appelle le service pour créer en base
    this.api.createUser(newUser).subscribe({
      next: createdUser => {
        // ici tu peux rediriger ou afficher un message
        console.log('Utilisateur créé :', createdUser);
        this.router.navigate(['/login']);
      },
      error: err => {
        console.error('Erreur à la création :', err);
        this.snackBar.open(err.error?.message || 'Erreur lors de l’inscription', 'Fermer', {
          duration: 2000
        });
      }
    });
  }
}
