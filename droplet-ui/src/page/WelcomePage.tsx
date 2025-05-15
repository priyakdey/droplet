import AppLayout from "@/layout/AppLayout.tsx";
import { useState } from "react";
import { Button } from "@/components/ui/button";
import "./WelcomePage.css";

function WelcomePage() {
  const [ isLoginTabSelected, setIsLoginTabSelected ] = useState<boolean>(true);

  return (
    <AppLayout className="WelcomePage-app-layout">
      <div className="WelcomePage-form-container">
        <div className="WelcomePage-form-header-container">
          <Button className="WelcomePage-form-header-btn" variant="ghost">
            Login
          </Button>
          <Button className="WelcomePage-form-header-btn" variant="ghost">
            Signup
          </Button>
        </div>
        {
          isLoginTabSelected ?
            <div className="WelcomePage-login-form-container">
              Login Form
            </div>
            :
            <div className="WelcomePage-login-form-container">
              Signup Form
            </div>
        }
      </div>
    </AppLayout>
  );
}

export default WelcomePage;