import LoginForm from "@/components/form/loginform/LoginForm.tsx";
import { Button } from "@/components/ui/button";
import AppLayout from "@/layout/AppLayout.tsx";
import { useState } from "react";
import "./WelcomePage.css";

function WelcomePage() {
  const [ isLoginTabSelected, setIsLoginTabSelected ] = useState<boolean>(true);

  return (
    <AppLayout className="WelcomePage-app-layout">
      <div className="WelcomePage-auth-container">
        <div className="WelcomePage-form-header-container">
          <Button
            className={`WelcomePage-form-header-btn ${isLoginTabSelected ? "active-tab" : ""}`}
            variant="ghost"
            onClick={() => setIsLoginTabSelected(true)}
          >
            Login
          </Button>
          <Button
            className={`WelcomePage-form-header-btn ${!isLoginTabSelected ? "active-tab" : ""}`}
            variant="ghost"
            onClick={() => setIsLoginTabSelected(false)}
          >
            Signup
          </Button>
        </div>
        {isLoginTabSelected ? <LoginForm /> : <p>Signup Form</p>}
      </div>
    </AppLayout>
  );
}

export default WelcomePage;