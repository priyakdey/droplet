import AppSidebar from "@/components/sidebar/AppSidebar.tsx";
import { SidebarProvider } from "@/components/ui/sidebar.tsx";
import { Directory } from "@/types/directory-ui.types.ts";
import * as React from "react";
import { Outlet } from "react-router-dom";
import "./Layout.css";

interface LayoutPropsTypes {
  directoryTree: Directory[],
  children?: React.ReactNode | null
}

function Layout({ directoryTree, children }: LayoutPropsTypes) {
  return (
    <SidebarProvider>
      <div className="page-container">
        <AppSidebar directoryTree={directoryTree} />
        <main className="main-content">
          {children}
          <Outlet />
        </main>
      </div>
    </SidebarProvider>
  );
}

export default Layout;