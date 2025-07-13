import ProtectedRoute from "@/components/route/ProtectedRoute.tsx";
import { Toaster } from "@/components/ui/sonner.tsx";
import HomePage from "@/page/home/HomePage.tsx";
import WelcomePage from "@/page/welcome/WelcomePage.tsx";
import { Route, Routes } from "react-router";

import "./App.css";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<WelcomePage />} />
        <Route path="/home" element={
          <ProtectedRoute>
            <HomePage />
          </ProtectedRoute>}
        />
      </Routes>
      <Toaster richColors theme="dark" dir="ltr" duration={5000} closeButton
               position="bottom-right" />
    </>
  );
}

export default App;
