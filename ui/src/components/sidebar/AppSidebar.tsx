import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel, SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem
} from "@/components/ui/sidebar";
import { useProfile } from "@/hooks/useProfile.ts";
import { Folder } from "lucide-react";

const directories = [
  {
    title: "Home",
    url: "home",
    icon: Folder
  },
  {
    title: "Inbox",
    url: "/home/inbox",
    icon: Folder
  },
  {
    title: "Calendar",
    url: "/home/calendar",
    icon: Folder
  },
  {
    title: "Search",
    url: "/home/search",
    icon: Folder
  },
  {
    title: "Settings",
    url: "/home/settings",
    icon: Folder
  },
  {
    title: "Folder",
    url: "/home/search/folder",
    icon: Folder
  }
];

function AppSidebar() {
  const { profile } = useProfile();

  return (
    <Sidebar>
      <SidebarHeader>Hello, {profile?.name}</SidebarHeader>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Directories</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {directories.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <a href={item.url}>
                      <item.icon />
                      <span>{item.title}</span>
                    </a>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}


export default AppSidebar;