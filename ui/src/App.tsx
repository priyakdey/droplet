import HomePage from "@/page/HomePage.tsx";
import LoginPage from "@/page/LoginPage.tsx";
import SignupPage from "@/page/SignupPage.tsx";
import ProtectedRoute from "@/routes/ProtectedRoute.tsx";
import { Route, Routes } from "react-router-dom";
import { Toaster } from "sonner";

import "./App.css";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route
          path="/home"
          element={
            <ProtectedRoute>
              <HomePage />
            </ProtectedRoute>
          }>
        </Route>
      </Routes>
      <Toaster richColors theme="dark" position="bottom-right"
               toastOptions={{ className: "custom-toast" }} closeButton />
    </>
  );
}

export default App;
