import Button from "@/components/button/Button.tsx";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem
} from "@/components/ui/sidebar";
import { useProfile } from "@/hooks/useProfile.ts";
import { Directory } from "@/types/directory-ui.types.ts";
import {
  ChevronDownSquareIcon,
  ChevronRightSquare,
  FolderClosed,
  FolderOpen
} from "lucide-react";
import { useState } from "react";

import "./AppSidebar.css";

interface AppSideBarPropsType {
  directoryTree: Directory[];
}

function AppSidebar({ directoryTree }: AppSideBarPropsType) {
  const { profile } = useProfile();

  return (
    <Sidebar>
      <SidebarHeader>Hello, {profile?.name}</SidebarHeader>
      <SidebarContent>
        <div className="app-sidebar-new-dir-container">
          <Button type="button" variant="default">
            + New Directory
          </Button>
        </div>
        <SidebarGroup>
          <SidebarGroupLabel>Directories</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {
                directoryTree.map((dir) => ((
                  <Dir key={dir.name} dir={dir} level={1} />
                )))
              }
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}

function Dir({ dir, level }: {
  dir: { name: string, url: string, children?: Directory[] },
  level: number
}) {

  const [ isExpanded, setIsExpanded ] = useState<boolean>(false);

  const toggleExpand = () => setIsExpanded((prev) => !prev);
  const style = { "paddingLeft": `${level * 12}px`};

  return (
    <>
      <SidebarMenuItem style={style}>
        <SidebarMenuButton onClick={toggleExpand}>
          {
            (dir.children?.length ?? 0) > 0
              ? isExpanded ? <ChevronDownSquareIcon /> : <ChevronRightSquare />
              : null
          }
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
            <Dir dir={child} key={child.name} level={level + 1} />
          ))
        }
      </div>
    </>
  );
}


export default AppSidebar;