import dropletSvgUrl from "@/assets/droplet.svg";
import Button from "@/components/button/Button.tsx";
import { useAuth } from "@/hooks/useAuth.ts";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import "./Header.css";

function Header() {
  const { isLoggedIn, logout } = useAuth();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate("/", { replace: true });
    toast.info("Successfully logged out", { duration: 5000 });
  }

  return (
    <header className="header-container">
      <img src={dropletSvgUrl} alt="Droplet logo" className="droplet-logo" />
      <div
        className="container flex flex-row justify-around items-center w-1/4">
        {
          isLoggedIn &&
          <Button className="h-8 w-30" type="button" variant="destructive"
                  onClick={handleLogout}>
            Logout
          </Button>
        }
      </div>
    </header>
  );
}

export default Header;