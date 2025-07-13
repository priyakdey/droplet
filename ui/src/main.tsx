import { AuthProvider } from "@/context/AuthContext.tsx";
import { ProfileDetailsProvider } from "@/context/ProfileDetailsContext.tsx";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import { BrowserRouter } from "react-router";
import App from "./App.tsx";

createRoot(document.getElementById("root")!).render(
  <BrowserRouter>
    <AuthProvider>
      <ProfileDetailsProvider>
        <StrictMode>
          <App />
        </StrictMode>
      </ProfileDetailsProvider>
    </AuthProvider>
  </BrowserRouter>
);
