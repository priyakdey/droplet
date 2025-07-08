import WelcomePage from "@/components/page/welcome/WelcomePage.tsx";
import { Route, Routes } from "react-router";

import "./App.css";

function App() {
  return (
    <Routes>
      <Route path="/" element={<WelcomePage />} />
    </Routes>
  );
}

export default App;
