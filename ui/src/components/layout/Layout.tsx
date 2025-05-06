import AppSidebar from "@/components/sidebar/AppSidebar.tsx";
import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar.tsx";
import { Outlet } from "react-router-dom";

function Layout() {
  return (
    <SidebarProvider>
      <div className="flex flex-col h-screen w-screen">
        <div className="flex flex-1">
          <AppSidebar />
          <main className="flex-1 overflow-y-auto bg-background p-4">
            <SidebarTrigger />
            <Outlet />
          </main>
        </div>
      </div>
    </SidebarProvider>
  );
}

export default Layout;