import Footer from "@/components/footer/Footer.tsx";
import Dashboard from "@/page/Dashboard.tsx";
import ProtectedRoute from "@/routes/ProtectedRoute.tsx";
import { Route, Routes } from "react-router-dom";
import { Toaster } from "sonner";

import Header from "./components/header/Header";
import Main from "./components/main/Main";

import "./App.css";

function App() {

  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/dashboard" element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        } />
      </Routes>
      <Footer />
      <Toaster richColors theme="dark" position="bottom-right"
               toastOptions={{ className: "custom-toast" }} closeButton />
    </>
  );
}

export default App;
