export interface Directory {
  id: string;
  name: string;
  url: string;
  children?: Directory[];
}