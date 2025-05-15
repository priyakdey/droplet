import Footer from "@/components/footer/Footer.tsx";
import Header from "@/components/header/Header";
import * as React from "react";

import "./AppLayout.css";

interface AppLayoutProps {
  className?: string;
  children?: React.ReactNode;
}

function AppLayout({ className, children }: AppLayoutProps) {
  return (
    <>
      <Header />
      <main className={`App-main ${className}`}>
        {children}
      </main>
      <Footer />
    </>
  );
}

export default AppLayout;
