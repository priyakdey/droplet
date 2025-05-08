import Dir from "@/components/dir/Dir.tsx";
import CreateDirForm from "@/components/form/CreateDirForm.tsx";
import Popover from "@/components/popover/Popover.tsx";
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
  activeDirId: string;
  setActiveDirId: (dir: string) => void;
  refreshDirectoryTree: () => void;
}

function AppSidebar({
                      directoryTree,
                      activeDirId,
                      setActiveDirId,
                      refreshDirectoryTree
                    }: AppSideBarPropsType) {
  const { profile } = useProfile();


  return (
    <Sidebar>
      <SidebarHeader className="app-sidebar-header">
        Welcome, {profile?.name}
      </SidebarHeader>
      <SidebarContent>
        <div className="app-sidebar-new-dir-container">
          <Popover>
            {
              (onClose) => (
                <CreateDirForm activeDirId={activeDirId}
                               setActiveDirId={setActiveDirId}
                               refreshDirectoryTree={refreshDirectoryTree}
                               onClose={onClose} />
              )
            }
          </Popover>
        </div>
        <SidebarGroup>
          <SidebarGroupLabel>Directories</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {
                directoryTree.map((dir) => ((
                  <Dir key={dir.name} dir={dir} level={0}
                       activeDirId={activeDirId}
                       setActiveDirId={setActiveDirId} />
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