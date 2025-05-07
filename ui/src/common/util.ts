import { DirectoryDto } from "@/types/directory-api.types.ts";
import { Directory } from "@/types/directory-ui.types.ts";

export function buildDirectoryTree(flat: DirectoryDto[]): Directory[] {
  const idToNodeMap: Record<string, Directory> = {};
  const root: Directory[] = [];

  for (const dir of flat) {
    idToNodeMap[dir.id] = {
      id: dir.id,
      name: dir.name,
      url: `/directory/${dir.name}`,
      children: []
    };
  }

  for (const dir of flat) {
    const node = idToNodeMap[dir.id];
    if (dir.parentId) {
      const parentNode = idToNodeMap[dir.parentId];
      if (parentNode) {
        parentNode.children!.push(node);
      }
      // TODO: We dont handle else part. Most likely to never happen, but if it does, unexpected error
    } else {
      root.push(node);
    }
  }

  return root;
}