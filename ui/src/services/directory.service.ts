import {
  API_V1_GET_ALL_DIRECTORIES,
  API_V1_CREATE_DIRECTORY
} from "@/common/constant.ts";
import {
  DirectoryListResponse, NewDirectoryRequest,
  NewDirectoryResponse
} from "@/types/directory-api.types.ts";
import { ErrorResponse } from "@/types/error-api.types.ts";


export async function getAllDirectories(): Promise<DirectoryListResponse> {
  const token = `Bearer ${localStorage.getItem("token")!}`;

  const response = await fetch(API_V1_GET_ALL_DIRECTORIES, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      Authorization: token
    }
  });

  if (!response.ok) {
    const body: ErrorResponse = await response.json();
    throw new Error(body.message);
  }

  return await response.json();
}

export async function createDirectory(newDirectoryRequest: NewDirectoryRequest): Promise<NewDirectoryResponse> {
  const token = `Bearer ${localStorage.getItem("token")!}`;

  const response = await fetch(API_V1_CREATE_DIRECTORY, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      Authorization: token
    },
    body: JSON.stringify(newDirectoryRequest)
  });

  if (response.status !== 201) {
    const body: ErrorResponse = await response.json();
    throw new Error(body.message);
  }

  return await response.json();
}