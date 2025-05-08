export interface DirectoryDto {
  id: string,
  name: string,
  ownerId: string,
  parentId: string | null,
  created_at: Date,
  updated_at: Date,
}

export interface DirectoryListResponse {
  directories: DirectoryDto[];
}

export interface NewDirectoryRequest {
  name: string;
  parentId: string;
}

export interface NewDirectoryResponse {
  id: string;
  name: string;
  parentId: string;
  ownerId: number;
  createdAt: Date;
  updatedAt: Date;
}