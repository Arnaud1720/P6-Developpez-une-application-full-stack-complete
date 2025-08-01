export interface SubscriptionDto {
  id?: number;
  userId?: number;
  subjectId?: number;
  subscribedAt?: string;    // ISO 8601, p.ex. "2025-07-01T12:34:56Z"
  unsubscribedAt?: string | null;
  postId?: number;

}
