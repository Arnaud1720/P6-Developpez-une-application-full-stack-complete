import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ApiService} from "../../services/api.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

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
  }

}
