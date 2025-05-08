import { DirectoryDto } from "@/types/directory-api.types.ts";
import { Directory } from "@/types/directory-ui.types.ts";

export function buildDirectoryTree(flat: DirectoryDto[]): {
  root: Directory[];
  idToDirectoryMap: Map<string, Directory>;
} {
  const idToDirectoryMap: Map<string, Directory> = new Map<string, Directory>();
  const root: Directory[] = [];

  for (const dir of flat) {
    const node = {
      id: dir.id,
      name: dir.name,
      url: "",
      children: [],
      parentId: dir.parentId
    };
    idToDirectoryMap.set(dir.id, node);

    if (dir.parentId === null) {
      root.push(node);
    }
  }

  for (const dir of flat) {
    const node = idToDirectoryMap.get(dir.id);
    if (dir.parentId !== null) {
      const parentNode = idToDirectoryMap.get(dir.parentId);
      if (parentNode && node) {
        parentNode.children!.push(node);
      }
    }
  }

  // Compute URLs for each node
  const buffer: string[] = [];
  computeUrls(root[0], buffer);

  return { root, idToDirectoryMap };
}

export function buildParentMap(idToDirectoryMap: Map<string, Directory>) {
  const idToParentMap: Map<string, Directory> = new Map<string, Directory>();

  for (const id of idToDirectoryMap.keys()) {
    const parentId = idToDirectoryMap.get(id)!.parentId;
    if (parentId === null) continue;
    const parent = idToDirectoryMap.get(parentId)!;
    idToParentMap.set(id, parent);
  }

  return idToParentMap;
}

function computeUrls(node: Directory, buffer: string[]) {
  const currentPath = node.name;
  buffer.push(currentPath);
  node.url = buffer.join("/");
  if (node.children) {
    for (const child of node.children) {
      computeUrls(child, buffer);
    }
  }
  buffer.pop();
}



