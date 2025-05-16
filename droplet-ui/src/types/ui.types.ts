export interface Directory {
  id: string;
  name: string;
  parent?: Directory | null;
  children: Directory[];
  createdAt: Date;
  updatedAt: Date;
}
