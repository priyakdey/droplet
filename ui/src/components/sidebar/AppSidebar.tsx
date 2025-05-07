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
import { getAllDirectories } from "@/services/directory.service.ts";
import { DirectoryDto } from "@/types/directory-api.types.ts";
import { Directory } from "@/types/directory-ui.types.ts";
import {
  ChevronDownSquareIcon,
  ChevronRightSquare,
  FolderClosed,
  FolderOpen
} from "lucide-react";
import { useEffect, useState } from "react";
import { toast } from "sonner";

function buildDirectoryTree(flat: DirectoryDto[]): Directory[] {
  const idToNodeMap: Record<string, Directory> = {};
  const root: Directory[] = [];

  for (const dir of flat) {
    idToNodeMap[dir.id] = {
      id: dir.id,
      name: dir.name,
      url: `/directory/${dir.name}`,
      children: []
    };
  }

  for (const dir of flat) {
    const node = idToNodeMap[dir.id];
    if (dir.parentId) {
      const parentNode = idToNodeMap[dir.parentId];
      if (parentNode) {
        parentNode.children!.push(node);
      }
      // TODO: We dont handle else part. This is probably an unexpected error case
    } else {
      root.push(node);
    }
  }

  return root;
}


function AppSidebar() {
  const { profile } = useProfile();
  const [ directories, setDirectories ] = useState<Directory[]>([]);


  useEffect(() => {
    getAllDirectories()
      .then(body => {
        const tree = buildDirectoryTree(body.directories);
        setDirectories(tree);
      })
      .catch(err => {
        console.log(err);
        toast.error("Failed to load directories", { duration: 5000 });
      });
  }, [ directories ]);

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
  dir: { name: string, url: string, children?: Directory[] },
  level: number
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