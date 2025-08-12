export interface CommentaireDto {
  id: number;
  contenu: string;
  createdAt: string;      // ISO
  auteurId?: number;      // selon ton DTO
  auteurUsername?: string;
}
