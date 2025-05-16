import AuthProvider from "@/context/AuthContext.tsx";
import ExplorerProvider from "@/context/ExplorerContext.tsx";
import ProfileProvider from "@/context/ProfileContext.tsx";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App.tsx";

import "./index.css";

createRoot(document.getElementById("root")!).render(
  <BrowserRouter>
    <AuthProvider>
      <ProfileProvider>
        <ExplorerProvider>
          <StrictMode>
            <App />
          </StrictMode>
        </ExplorerProvider>
      </ProfileProvider>
    </AuthProvider>
  </BrowserRouter>
);
