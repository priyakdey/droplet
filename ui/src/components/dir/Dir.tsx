import {
  SidebarMenuButton,
  SidebarMenuItem
} from "@/components/ui/sidebar.tsx";
import { Directory } from "@/types/directory-ui.types.ts";
import {
  ChevronDownSquareIcon,
  ChevronRightSquare,
  FolderClosed,
  FolderOpen
} from "lucide-react";
import { useState } from "react";
import "./Dir.css";

interface DirPropsType {
  dir: Directory;
  level: number;
  activeDirId: string;
  setActiveDirId: (dirId: string) => void;
}

function Dir({ dir, level, activeDirId, setActiveDirId }: DirPropsType) {
  const [ isExpanded, setIsExpanded ] = useState<boolean>(false);

  const style = { "paddingLeft": `${level * 12}px` };

  const onChevronClick = () => setIsExpanded((prev) => !prev);
  const onDirClick = () => setActiveDirId(dir.id);

  return (
    <>
      <SidebarMenuItem style={style}>
        <SidebarMenuButton isActive={activeDirId === dir.id}
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
                 activeDirId={activeDirId} setActiveDirId={setActiveDirId} />
          ))
        }
      </div>
    </>
  );
}

export default Dir;