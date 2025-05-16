import type { AllDirectoriesResponse } from "@/types/api.types.ts";

export async function getAllDirectories(token: string): Promise<AllDirectoriesResponse> {
  const response = await fetch("http://localhost:8080/v1/directories", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${token}`
    }
  });

  if (response.status !== 200) {
    const errorResponse = await response.json();
    console.error(`ERROR: status code: ${errorResponse.statusCode} - ${errorResponse.message}`);
    throw new Error("Something Went Wrong", { cause: errorResponse.message });
  }

  return response.json();
}