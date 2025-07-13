import type {
  ErrorResponse,
  ProfileDetailsResponse
} from "@/types/api-types.ts";
import { AppError } from "@/types/error.ts";

export async function getProfileDetails(): Promise<ProfileDetailsResponse> {
  const response = await fetch("http://localhost:8080/me", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json"
    },
    credentials: "include"
  });

  if (!response.ok) {
    const errResponse = await response.json() as ErrorResponse;
    throw new AppError(response.status, errResponse.title,
      errResponse.description);
  }

  return await response.json() as ProfileDetailsResponse;
}