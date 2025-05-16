import type { DirectoryDto } from "@/types/api.types.ts";
import type { Directory } from "@/types/ui.types.ts";
import * as React from "react";
import { createContext, useState } from "react";

interface ExplorerContextType {
  isLoading: boolean;
  root: Directory | null;
  idToDirectoryMap: Map<string, Directory>;
  selectedId: string | null;
  buildTree: (dirs: DirectoryDto[]) => void;
}

// eslint-disable-next-line react-refresh/only-export-components
export const ExplorerContext = createContext<ExplorerContextType | null>(null);

interface ExplorerProviderPropsType {
  children: React.ReactNode;
}

function ExplorerProvider({ children }: ExplorerProviderPropsType) {
  const [ root, setRoot ] = useState<Directory | null>(null);
  const [ selectedId, setSelectedId ] = useState<string | null>(null);
  const [ idToDirectoryMap, setIdToDirectoryMap ] = useState<Map<string, Directory>>(new Map());
  const [ isLoading, setIsLoading ] = useState<boolean>(true);

  function buildTree(dirs: DirectoryDto[]) {
    const nodeMap = new Map<string, Directory>();

    for (const dir of dirs) {
      const id = dir.id;
      const directory: Directory = {
        id: dir.id,
        name: dir.name,
        parent: null,
        children: [],
        createdAt: dir.createdAt,
        updatedAt: dir.updatedAt
      };
      nodeMap.set(id, directory);
    }

    let _root: Directory | null = null;

    for (const dir of dirs) {
      const node = nodeMap.get(dir.id)!;
      const parentId = dir.parentId;
      if (!parentId) {
        _root = node!;
      } else {
        const parent = nodeMap.get(parentId!)!;
        if (parent) {
          parent.children.push(node!);
          node.parent = parent;
        }
      }
    }

    setRoot(_root);
    setIdToDirectoryMap(nodeMap);
    setSelectedId(_root?.id ?? null);
    setIsLoading(false);
  }

  return (
    <ExplorerContext.Provider
      value={{
        isLoading, root, idToDirectoryMap, selectedId, buildTree
      }}>
      {children}
    </ExplorerContext.Provider>
  );
}

export default ExplorerProvider;
