import { Component } from "@angular/core";
import {Router, RouterLink, RouterLinkWithHref} from "@angular/router";
import {ApiService} from "../../services/api.service";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";
@Component({
  selector: "app-header",
  standalone: true,
  templateUrl: "./header.component.html",
  imports: [
    MatButtonModule,
    RouterLinkWithHref,
    RouterLink,
    MatIconModule,
    NgIf
  ],
  styleUrls: ["./header.component.scss"]
})
export class HeaderComponent{
  constructor(
    public api:ApiService,  // ‹ public › pour le template
    private router: Router
  ) {}

  logout() {
    this.api.clearToken();
    this.router.navigate(['/']);
  }
}
