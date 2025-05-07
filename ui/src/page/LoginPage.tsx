import Footer from "@/components/footer/Footer.tsx";
import { LoginForm } from "@/components/form/LoginForm";
import Header from "@/components/header/Header.tsx";
import { useAuth } from "@/hooks/useAuth";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./AuthPage.css";

function LoginPage() {
  const { isLoggedIn } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isLoggedIn) {
      navigate("/home", { replace: true });
    }
  }, [ isLoggedIn, navigate ]);

  return (
    <div className="page-container">
      <Header />
      <main className="form-main-content">
        <LoginForm handleSignupClick={() => navigate("/signup")} />
      </main>
      <Footer />
    </div>
  );
}

export default LoginPage;
