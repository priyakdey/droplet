import { Toaster } from "@/components/ui/sonner.tsx";
import WelcomePage from "@/page/WelcomePage.tsx";
import { Route, Routes } from "react-router-dom";

import "./App.css";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<WelcomePage />} />
      </Routes>
      <Toaster richColors theme="dark" position="bottom-right" closeButton
               duration={5000} toastOptions={{ className: "custom-toast" }} />
    </>
  );
}

export default App;
