export interface LoginRequest {
  email: string;
  password: string;
}

interface ProfileMetadataDto {
  accountId: number;
  profileId: number;
  homeDirId: string;
  name: string;
  timeZone: string;
}

export interface AuthResponse {
  profile: ProfileMetadataDto;
  token: string;
}

export interface ErrorResponse {
  statusCode: number;
  message: string;
}