export interface LoginRequest {
  email: string;
  password: string;
}

interface ProfileMetadataDto {
  accountId: number;
  profileId: number;
  homeDirId: string;
  name: string;
  timezone: string;
}

export interface AuthResponse {
  profile: ProfileMetadataDto;
  token: string;
}

export interface ErrorResponse {
  statusCode: number;
  message: string;
}