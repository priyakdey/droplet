import { Toaster } from "@/components/ui/sonner.tsx";
import HomePage from "@/page/homepage/HomePage.tsx";
import WelcomePage from "@/page/welcomepage/WelcomePage.tsx";
import { Route, Routes } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import "./App.css";


function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<WelcomePage />} />
        <Route path="/home/*" element={
          <ProtectedRoute>
              <HomePage />
          </ProtectedRoute>
        }></Route>
      </Routes>
      <Toaster richColors theme="dark" position="bottom-right" closeButton
               duration={5000} toastOptions={{ className: "custom-toast" }} />
    </>
  );
}

export default App;
