export interface JwtPayload {
  sub: string;            // l’identifiant (username ou email)
  exp: number;            // date d’expiration en timestamp Unix (secondes)
  iat?: number;           // date d’émission (optionnel)
  usernameOrEmail?: string; // si tu l’inclues dans ton token

}
