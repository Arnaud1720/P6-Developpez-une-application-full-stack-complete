export interface SubscriptionDto {
  id?: number;
  userId?: number;
  themeId?: number;
  themeName?: string;           // Ajout pour correspondre au DTO Java
  themeDescription?: string;    // Ajout pour correspondre au DTO Java
  subscribedAt?: string;
  unsubscribedAt?: string | null;
}
