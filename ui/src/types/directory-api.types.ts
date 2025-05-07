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
