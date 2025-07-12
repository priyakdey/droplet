import HomePage from "@/page/home/HomePage.tsx";
import WelcomePage from "@/page/welcome/WelcomePage.tsx";
import { Route, Routes } from "react-router";

import "./App.css";

function App() {
  return (
    <Routes>
      <Route path="/" element={<WelcomePage />} />
      <Route path="/home" element={<HomePage />} />
    </Routes>
  );
}

export default App;
