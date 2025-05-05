import Footer from "@/components/footer/Footer.tsx";
import HomePage from "@/page/HomePage.tsx";
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
        <Route path="/home" element={
          <ProtectedRoute>
            <HomePage />
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
