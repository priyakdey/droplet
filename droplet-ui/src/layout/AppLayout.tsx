import Footer from "@/components/footer/Footer.tsx";
import Header from "@/components/header/Header";
import AppSidebar from "@/components/sidebar/AppSidebar.tsx";
import { SidebarProvider } from "@/components/ui/sidebar.tsx";
import useAuth from "@/hooks/useAuth.ts";
import * as React from "react";
import "./AppLayout.css";

interface AppLayoutProps {
  className?: string;
  children?: React.ReactNode;
}

function AppLayout({ className, children }: AppLayoutProps) {
  const { isLoggedIn } = useAuth();

  const renderMainContent = () => (
    <div className="App-main-wrapper">
      {isLoggedIn && <AppSidebar />}
      <main className={`App-main ${className}`}>
        {children}
      </main>
    </div>
  );

  return (
    <div className="App-layout">
      <Header />
      {
        isLoggedIn
          ? <SidebarProvider>{renderMainContent()}</SidebarProvider>
          : renderMainContent()
      }
      <Footer />
    </div>
  );
}

export default AppLayout;
