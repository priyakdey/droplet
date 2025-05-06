import AppSidebar from "@/components/sidebar/AppSidebar.tsx";
import { SidebarProvider } from "@/components/ui/sidebar.tsx";
import * as React from "react";
import { Outlet } from "react-router-dom";
import "./Layout.css";

function Layout({ children }: { children?: React.ReactNode | null }) {
  return (
    <SidebarProvider>
      <div className="page-container">
        <AppSidebar />
        <main className="main-content">
          {children}
          <Outlet />
        </main>
      </div>
    </SidebarProvider>
  );
}

export default Layout;