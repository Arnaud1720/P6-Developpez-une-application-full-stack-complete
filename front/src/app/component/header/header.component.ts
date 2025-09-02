import { Component } from "@angular/core";
import { Router, RouterLink, RouterLinkWithHref } from "@angular/router";
import { ApiService } from "../../services/api.service";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { NgIf } from "@angular/common";
import { MatToolbarModule } from "@angular/material/toolbar";

@Component({
  selector: "app-header",
  standalone: true,
  templateUrl: "./header.component.html",
  imports: [
    MatButtonModule,
    RouterLinkWithHref,
    RouterLink,
    MatIconModule,
    NgIf,
    MatToolbarModule
  ],
  styleUrls: ["./header.component.scss"]
})
export class HeaderComponent {
  isMobileMenuOpen = false;

  constructor(
    public api: ApiService,
    private router: Router
  ) {}

  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;

    if (this.isMobileMenuOpen) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = 'auto';
    }
  }

  closeMobileMenu(): void {
    this.isMobileMenuOpen = false;
    document.body.style.overflow = 'auto';
  }

  logout(): void {
    this.api.clearToken();
    this.router.navigate(['/']);
  }

  onResize(): void {
    if (window.innerWidth >= 768 && this.isMobileMenuOpen) {
      this.closeMobileMenu();
    }
  }
}
