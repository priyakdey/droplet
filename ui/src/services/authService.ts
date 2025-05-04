import { API_V1_SIGNUP } from "@/common/constant";
import { SignupRequest, SignupResponse } from "@/types/auth.ts";

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
    throw new Error(`Signup failed: ${response.statusText}`);
  }

  return await response.json();
}