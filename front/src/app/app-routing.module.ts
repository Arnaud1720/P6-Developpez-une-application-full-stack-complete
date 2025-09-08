import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {LoginComponent} from "./auth/login/login.component";
import {RegisterComponent} from "./auth/register/register.component";
import {ProfilComponent} from "./component/profil/profil.component";
import {PostsComponent} from "./component/posts/posts.component";
import {CreatePostComponent} from "./component/create-post/create-post.component";
import {PostDetailComponent} from "./component/post-detail/post-detail.component";
import {ThemesComponent} from "./component/themes/themes.component";


const routes: Routes = [
  { path: '',           component: HomeComponent },
  { path: 'login',      component: LoginComponent },
  { path: 'register',   component: RegisterComponent },
  { path: 'profil', component: ProfilComponent },
  { path: 'posts', component: PostsComponent },
  { path: 'posts/:id', component: PostDetailComponent },
  {path:'add-post',component:CreatePostComponent},
  {path:'themes',component: ThemesComponent},
  { path: '**',         redirectTo: '' }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],

})

export class AppRoutingModule {

}
