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
import {
  ChevronDownSquareIcon,
  ChevronRightSquare,
  Folder,
  FolderClosed,
  FolderOpen
} from "lucide-react";
import { useState } from "react";

const directories = [
  {
    name: "Home",
    url: "/home",
    icon: Folder,
    children: [
      {
        name: "Documents",
        url: "/home/documents",
        icon: Folder,
        children: [
          {
            name: "Reports",
            url: "/home/documents/reports",
            icon: Folder
          },
          {
            name: "Invoices",
            url: "/home/documents/invoices",
            icon: Folder
          }
        ]
      },
      {
        name: "Downloads",
        url: "/home/downloads",
        icon: Folder
      }
    ]
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
              {
                directories.map((dir) => ((
                  <Dir key={dir.name} dir={dir} level={0} />
                )))
              }
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  )
    ;
}

function Dir({ dir, level }: {
  dir: { name: string, url: string, icon: any, children?: any[] }, level: number
}) {

  const [ isExpanded, setIsExpanded ] = useState<boolean>(false);

  const toggleExpand = () => setIsExpanded((prev) => !prev);
  const className = `pl-${level * 4}`;

  return (
    <SidebarMenuItem className={className}>
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

      {
        isExpanded && (dir.children?.length ?? 0) > 0 &&
        dir.children!.map((child) => (
          <Dir dir={child} key={child.name} level={level + 1} />
        ))
      }

    </SidebarMenuItem>
  );
}


export default AppSidebar;