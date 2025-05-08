import Button from "@/components/button/Button.tsx";
import Dir from "@/components/dir/Dir.tsx";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu
} from "@/components/ui/sidebar";
import { useProfile } from "@/hooks/useProfile.ts";
import { Directory } from "@/types/directory-ui.types.ts";

import "./AppSidebar.css";

interface AppSideBarPropsType {
  directoryTree: Directory[];
  currDir: Directory | null;
  setCurrDir: (dir: Directory) => void;
}

function AppSidebar({
                      directoryTree,
                      currDir,
                      setCurrDir
                    }: AppSideBarPropsType) {
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
                  <Dir key={dir.name} dir={dir} level={0}
                       activeDir={currDir}
                       setActiveDir={setCurrDir} />
                )))
              }
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}

export default AppSidebar;