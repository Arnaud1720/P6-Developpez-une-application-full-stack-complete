import {SubscriptionDto} from "./SubscriptionDto";

export interface ProfilDto{
  id: number;
  firstname: string;
  lastname: string;
  email: string;
  topicsCreatedCount: number;
  subscriptionsCount: number;
  subscribedSubjectsCount: number;
  subscriptions: SubscriptionDto[];
}
