export interface SignupRequest {
  name: string;
  email: string;
  password: string;
}

export interface SignupResponse {
  id: number;
  name: string;
  token: string;
}

export interface ErrorResponse {
  message: string;
}