import { API_V1_SIGNUP } from "@/common/constant";
import { ErrorResponse, SignupRequest, SignupResponse } from "@/types/auth.ts";

export async function signup(signupRequest: SignupRequest): Promise<SignupResponse> {
  const body = JSON.stringify(signupRequest);

  const response = await fetch(API_V1_SIGNUP, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Content-Length": body.length.toString(),
      "Accept": "application/json"
    },
    body: body
  });

  if (response.status !== 201) {
    const body: ErrorResponse = await response.json();
    throw new Error(`Signup failed: ${body.message}`);
  }

  return await response.json();
}