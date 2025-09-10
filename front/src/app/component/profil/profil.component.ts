import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ProfilDto } from "../../models/ProfilDto";
import { ApiService } from "../../services/api.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { SubscriptionDto } from '../../models/SubscriptionDto';
import { MatSnackBar } from '@angular/material/snack-bar';
import {UpdateProfileDto} from "../../models/UpdateProfileDto";

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {
  @ViewChild('updateDialog') updateDialog!: TemplateRef<any>;

  profile?: ProfilDto;
  loading = false;
  errorMsg = '';
  processingUnsubscribe = false;
  updateForm!: FormGroup;

  constructor(
    private api: ApiService,
    private fb: FormBuilder,
    private dialog: MatDialog,
    private router: Router,
    private snackBar: MatSnackBar
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

  loadProfile(): void {
    this.loading = true;
    this.errorMsg = '';

    this.api.myProfil().subscribe({
      next: dto => {
        this.profile = dto;
        // Filtrer uniquement les souscriptions actives (sans unsubscribedAt)
        if (this.profile.subscriptions) {
          this.profile.subscriptions = this.profile.subscriptions.filter(
            sub => !sub.unsubscribedAt
          );
        }
        this.loading = false;
        // Mettre à jour le formulaire avec l'email actuel
        if (dto.email) {
          this.updateForm.patchValue({ email: dto.email });
        }
      },
      error: (err) => {
        console.error('Erreur chargement profil:', err);
        this.errorMsg = 'Impossible de charger le profil';
        this.loading = false;
      }
    });
  }

  private initForm(currentEmail: string) {
    this.updateForm = this.fb.group({
      email: [currentEmail, [Validators.email]],
      password: ['', [Validators.minLength(6)]]
    });
  }

  get email()    { return this.updateForm.get('email')!; }
  get password() { return this.updateForm.get('password')!; }

  openUpdateDialog(): void {
    if (!this.updateDialog) {
      // Si le ViewChild n'est pas encore chargé, utiliser setTimeout
      setTimeout(() => this.openUpdateDialog(), 100);
      return;
    }

    const dialogRef = this.dialog.open(this.updateDialog, {
      width: '500px',
      disableClose: false
    });
  }

  onUpdate() {
    if (this.updateForm.invalid || !this.profile) return;

    const dto: UpdateProfileDto = {
      email: this.email.value || undefined,
      password: this.password.value || undefined  // Ne pas envoyer si vide
    };

    // Nettoyer l'objet pour ne pas envoyer de champs undefined
    const cleanDto: UpdateProfileDto = {};
    if (dto.email) cleanDto.email = dto.email;
    if (dto.password) cleanDto.password = dto.password;

    console.log('Envoi de la mise à jour:', cleanDto); // Debug

    this.api.updateProfil(cleanDto).subscribe({
      next: updated => {
        // Mettre à jour l'affichage
        this.profile!.email = updated.email;
        // Réinitialiser le mot de passe
        this.updateForm.get('password')!.reset();
        console.log('Profil mis à jour avec succès');
      },
      error: err => {
        console.error('Erreur update profil:', err);
        if (err.error) {
          console.error('Détails:', err.error);
        }
      }
    });
  }

  unsubscribe(subscription: SubscriptionDto): void {
    if (!subscription.id || this.processingUnsubscribe) return;

    // Confirmation avant désabonnement
    const confirmMessage = `Voulez-vous vraiment vous désabonner du thème "${subscription.themeName}" ?`;
    if (!confirm(confirmMessage)) return;

    this.processingUnsubscribe = true;

    this.api.removeSubscription(subscription.id).subscribe({
      next: () => {
        // Retirer la souscription de la liste
        if (this.profile && this.profile.subscriptions) {
          this.profile.subscriptions = this.profile.subscriptions.filter(
            sub => sub.id !== subscription.id
          );
          // Mettre à jour le compteur
          this.profile.subscriptionsCount = this.profile.subscriptions.length;
        }

        this.processingUnsubscribe = false;

        this.snackBar.open('Désabonnement effectué avec succès', 'Fermer', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom'
        });
      },
      error: (err) => {
        console.error('Erreur désabonnement:', err);
        this.processingUnsubscribe = false;

        this.snackBar.open('Erreur lors du désabonnement', 'Fermer', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom'
        });
      }
    });
  }

  logout(): void {
    // Confirmation avant déconnexion
    if (confirm('Êtes-vous sûr de vouloir vous déconnecter ?')) {
      this.api.clearToken();
      this.router.navigate(['/login']);
    }
  }
}
