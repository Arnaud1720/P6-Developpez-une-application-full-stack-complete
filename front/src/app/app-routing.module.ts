import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {LoginComponent} from "./auth/login/login.component";
import {RegisterComponent} from "./auth/register/register.component";
import {ProfilComponent} from "./component/profil/profil.component";
import {PostsComponent} from "./component/posts/posts.component";
import {CreatePostComponent} from "./component/create-post/create-post.component";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '',           component: HomeComponent },    // racine
  { path: 'login',      component: LoginComponent },   // /login
  { path: 'register',   component: RegisterComponent },// /register
  { path: 'profil', component: ProfilComponent },
  { path: 'posts', component: PostsComponent },
  {path:'add-post',component:CreatePostComponent},
  { path: '**',         redirectTo: '' }               // wildcard
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],

})

export class AppRoutingModule {

}
