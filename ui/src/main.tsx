import { AuthProvider } from "@/context/AuthContext.tsx";
import { ProfileProvider } from "@/context/ProfileContext.tsx";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { BrowserRouter } from "react-router-dom";

createRoot(document.getElementById("root")!).render(
  <BrowserRouter>
    <AuthProvider>
      <ProfileProvider>
        <StrictMode>
          <App />
        </StrictMode>
      </ProfileProvider>
    </AuthProvider>
  </BrowserRouter>
);
