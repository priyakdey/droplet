import { LoginForm } from "@/components/form/LoginForm.tsx";
import SignupForm from "@/components/form/SignupForm.tsx";
import { useState } from "react";
import "./Main.css";

function Main() {

  const [ isLogin, setIsLogin ] = useState<boolean>(false);
  const [ isSignup, setIsSignup ] = useState<boolean>(true);

  const handleLoginClick = () => {
    setIsLogin(true);
    setIsSignup(false);
  };

  const handleSignupClick = () => {
    setIsLogin(false);
    setIsSignup(true);
  };

  return (
    <main className="main">
      {isLogin && <LoginForm handleSignupClick={handleSignupClick} />}
      {isSignup && <SignupForm handleLoginClick={handleLoginClick} />}
    </main>
  );
}

export default Main;