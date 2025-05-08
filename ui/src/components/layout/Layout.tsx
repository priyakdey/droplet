import AppSidebar from "@/components/sidebar/AppSidebar.tsx";
import { SidebarProvider } from "@/components/ui/sidebar.tsx";
import { Directory } from "@/types/directory-ui.types.ts";
import * as React from "react";
import { Outlet } from "react-router-dom";
import "./Layout.css";

interface LayoutPropsTypes {
  directoryTree: Directory[],
  currDir: Directory | null;
  setCurrDir: (dir: Directory) => void;
  children?: React.ReactNode | null;
}

function Layout({
                  directoryTree,
                  currDir,
                  setCurrDir,
                  children
                }: LayoutPropsTypes) {
  return (
    <SidebarProvider>
      <div className="page-container">
        <AppSidebar directoryTree={directoryTree} currDir={currDir}
                    setCurrDir={setCurrDir} />
        <main className="main-content">
          {children}
          <Outlet />
        </main>
      </div>
    </SidebarProvider>
  );
}

export default Layout;