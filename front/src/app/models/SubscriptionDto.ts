export interface SubscriptionDto {
  id?: number;
  userId?: number;
  themeId?: number;
  subscribedAt?: string;
  unsubscribedAt?: string | null;
  name?: string;
}
