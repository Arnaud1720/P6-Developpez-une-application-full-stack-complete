
export interface PostDto {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  userId: number;
  subjectId: number;
  authorName: string;
  postId?:number
  authorId?: number;
  authorPseudo?: string;
  auteurUsername: string;
  topicName: string;

}
