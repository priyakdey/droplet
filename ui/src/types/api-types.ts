export interface ErrorResponse {
  title: string;
  description?: string;
}

export interface ProfileDetailsResponse {
  profileId: string;
  name: string;
  container: string;
  avatarUrl: string;
}