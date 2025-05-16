import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupLabel,
  SidebarHeader
} from "@/components/ui/sidebar.tsx";
import useProfile from "@/hooks/useProfile.ts";


function AppSidebar() {
  const {name} = useProfile();

  return (
    <Sidebar>
      <SidebarHeader>
        Hello, {name}
      </SidebarHeader>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Application</SidebarGroupLabel>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  )
}

export default AppSidebar;