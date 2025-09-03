import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ApiService} from "../../services/api.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserDto} from "../../models/UserDto";
import {PostDto} from "../../models/PostDto";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {
  registerForm!: FormGroup;
  hide = true;


  constructor( private api: ApiService,
               private router: Router,
               private snackBar: MatSnackBar,
               private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      theme: ['', [Validators.required, Validators.minLength(4)]],
      title: ['', [Validators.required, Validators.minLength(4)]],
      content: ['', [Validators.required, Validators.maxLength(500)]],
    })
  }

  onSubmit() {
    if (!this.registerForm.valid) {
      return;
    }
    // 1) Récupère les valeurs du formulaire
    const postDto: PostDto = this.registerForm.value;
    //Appelle le service pour crée en base
    this.api.createPost(postDto).subscribe({
      next: resp => {
        console.log('un post a été crée',postDto);
        this.router.navigate(['/posts']);
      },
      error: err => {
        console.log("Erreur a la création du post",err);
        this.snackBar.open(err.error?.message || 'Erreur lors du post','fermer',{duration:3000})
      }
    })
  }

}
