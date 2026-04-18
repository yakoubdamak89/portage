export interface JwtPayload {
  sub: string;
  email: string;
  role: string;
  userId: number;
  consultantId?: number;
  firstName: string;
  lastName: string;
  iat: number;
  exp: number;
}
