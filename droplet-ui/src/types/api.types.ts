export interface ErrorResponse {
  statusCode: number;
  message: string;
}

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

export type AvailableTimezonesResponse = string[];

export interface DirectoryDto {
  id: string;
  name: string;
  parentId?: string | null;
  createdAt: Date;
  updatedAt: Date;
}

export interface AllDirectoriesResponse {
  directories: DirectoryDto[];
}