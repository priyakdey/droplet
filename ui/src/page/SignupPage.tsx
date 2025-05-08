import Footer from "@/components/footer/Footer.tsx";
import SignupForm from "@/components/form/SignupForm";
import Header from "@/components/header/Header.tsx";
import { useAuth } from "@/hooks/useAuth";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function SignupPage() {
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
        <SignupForm handleLoginClick={() => navigate("/")} />
      </main>
      <Footer />
    </div>
  );
}

export default SignupPage;
