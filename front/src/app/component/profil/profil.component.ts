import { Component, OnInit } from '@angular/core';
import {ProfilDto} from "../../models/ProfilDto";
import {ApiService} from "../../services/api.service";
import {UserDto} from "../../models/UserDto";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {
  profile?: ProfilDto;
  loading = false;
  errorMsg = '';

  updateForm!: FormGroup;

  constructor(
    private api: ApiService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.api.myProfil().subscribe({
      next: dto => {
        this.profile = dto;
        this.initForm(dto.email);
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Impossible de charger le profil';
        this.loading = false;
      }
    });
  }

  private initForm(currentEmail: string) {
    this.updateForm = this.fb.group({
      email: [currentEmail, [ Validators.email]],
      password: ['', [Validators.minLength(6)]]
    });
  }

  get email()    { return this.updateForm.get('email')!; }
  get password() { return this.updateForm.get('password')!; }

  onUpdate() {
    if (this.updateForm.invalid || !this.profile) return;

    const dto: UserDto = {
      id: this.profile.id,
      email: this.email.value,
      password: this.password.value
    };

    this.api.updateProfil(dto).subscribe({
      next: updated => {
        // Mets à jour l’affichage
        this.profile!.email = updated.email;
        // Réinitialise le mot de passe pour ne pas le renvoyer
        this.updateForm.get('password')!.reset();
      },
      error: err => {
        console.error('Erreur update profil', err);
      }
    });
  }

}
