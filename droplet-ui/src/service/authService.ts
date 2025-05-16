import type {
  AuthResponse,
  ErrorResponse,
  LoginRequest
} from "@/types/api.types.ts";

export async function authenticate(requestBody: LoginRequest): Promise<AuthResponse> {
  const body = JSON.stringify(requestBody);

  const response = await fetch("http://localhost:8080/v1/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json"
    },
    body: body
  });

  if (response.status !== 200) {
    const errorResponse: ErrorResponse = await response.json();
    console.error(`ERROR: status code: ${errorResponse.statusCode} - ${errorResponse.message}`);
    throw new Error("Login Failed", { cause: errorResponse.message });
  }

  return await response.json();
}