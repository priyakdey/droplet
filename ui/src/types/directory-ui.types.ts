export interface Directory {
  id: string;
  name: string;
  url: string;
  parentId: string | null;
  children?: Directory[];
}