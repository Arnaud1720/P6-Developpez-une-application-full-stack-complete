export interface PostDto {
  id: number;
  title: string;
  content: string;
  createdAt: string;    // ISO date reçue du back
  userId: number;
  subjectId: number;

}
