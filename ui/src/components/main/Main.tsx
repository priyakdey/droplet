import { LoginForm } from "@/components/form/LoginForm.tsx";
import SignupForm from "@/components/form/SignupForm.tsx";
import { useAuth } from "@/hooks/useAuth.ts";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Main.css";

function Main() {
  const [ isLogin, setIsLogin ] = useState<boolean>(true);
  const [ isSignup, setIsSignup ] = useState<boolean>(false);

  const { isLoggedIn } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isLoggedIn) {
      navigate("/home", { replace: true });
    }
  }, [ isLoggedIn, navigate ]);

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