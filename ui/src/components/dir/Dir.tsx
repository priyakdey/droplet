import {
  SidebarMenuButton,
  SidebarMenuItem
} from "@/components/ui/sidebar.tsx";
import { Directory } from "@/types/directory-ui.types.ts";
import {
  ChevronDownSquareIcon,
  ChevronRightSquare, FolderClosed,
  FolderOpen
} from "lucide-react";
import { useState } from "react";
import "./Dir.css";

interface DirPropsType {
  dir: Directory;
  level: number;
  activeDir: Directory | null;
  setActiveDir: (dir: Directory) => void;
}

function Dir({ dir, level, activeDir, setActiveDir }: DirPropsType) {
  const [ isExpanded, setIsExpanded ] = useState<boolean>(false);

  const style = { "paddingLeft": `${level * 12}px` };

  const onChevronClick = () => setIsExpanded((prev) => !prev);
  const onDirClick = () => setActiveDir(dir);

  return (
    <>
      <SidebarMenuItem style={style}>
        <SidebarMenuButton isActive={activeDir!.id === dir.id}
                           onClick={onDirClick}>
          <div role="button" aria-roledescription="works like a button"
               tabIndex={0} onClick={onChevronClick}
               style={{ height: "16", width: "16px" }}>
            {
              (dir.children?.length ?? 0) > 0
                ? isExpanded ? <ChevronDownSquareIcon size={16} /> :
                  <ChevronRightSquare size={16} />
                : null
            }
          </div>
          {
            (dir.children?.length ?? 0) > 0
              ? isExpanded ? <FolderOpen /> : <FolderClosed />
              : <FolderClosed />
          }
          <span>{dir.name}</span>
        </SidebarMenuButton>
      </SidebarMenuItem>
      <div
        className={`sidebar-folder-expandable ${isExpanded ? "expanded" : ""}`}>
        {
          isExpanded && (dir.children?.length ?? 0) > 0 &&
          dir.children!.map((child) => (
            <Dir dir={child} key={child.name} level={level + 1}
                 activeDir={activeDir} setActiveDir={setActiveDir} />
          ))
        }
      </div>
    </>
  );
}

export default Dir;