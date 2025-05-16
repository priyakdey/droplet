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
} from "@/components/ui/sidebar.tsx";
import useExplorer from "@/hooks/useExplorer.ts";
import useProfile from "@/hooks/useProfile.ts";
import type { Directory } from "@/types/ui.types";


function AppSidebar() {
  const { name } = useProfile();
  const explorerContext = useExplorer();

  if (!explorerContext?.root) return null;    // TODO: give a spinner

  return (
    <Sidebar>
      <SidebarHeader>
        Hello, {name}
      </SidebarHeader>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Directories</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              <DirectoryMenu dir={explorerContext?.root} level={0} />
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}

interface DirectoryMenuPropsType {
  dir: Directory;
  level: number;
}

function DirectoryMenu({ dir, level }: DirectoryMenuPropsType) {
  // const [ isExpanded, setIsExpanded ] = useState<boolean>(false);
  const { selectedId } = useExplorer();

  const style = { "paddingLeft": `${level * 12}px` };

  return (
    <>
      <SidebarMenuItem style={style}>
        <SidebarMenuButton asChild isActive={dir?.id === selectedId}>
          <p>{dir.name}</p>
        </SidebarMenuButton>
      </SidebarMenuItem>
      {
        dir.children && dir.children.length > 0 &&
        dir.children.map((subDir: Directory) => (
          <DirectoryMenu key={subDir.name} dir={subDir} level={level + 1} />
        ))
      }
    </>
  );
}

export default AppSidebar;
