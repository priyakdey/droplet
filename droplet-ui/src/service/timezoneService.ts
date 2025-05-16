import type {
  AvailableTimezonesResponse,
  ErrorResponse
} from "@/types/api.types.ts";

export async function getAllAvailableTimezones(token: string): Promise<AvailableTimezonesResponse> {
  const response = await fetch("http://localhost:8080/v1/available-timezones", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${token}`
    }
  });

  if (response.status !== 200) {
    const errorResponse: ErrorResponse = await response.json();
    console.error(`ERROR: status code: ${errorResponse.statusCode} - ${errorResponse.message}`);
    throw new Error("Could not fetch timezones", { cause: errorResponse.message });
  }

  return response.json();
}