import dropletLogo from "@/assets/droplet-logo.svg";
import githubLogo from "@/assets/github-mark.svg";
import { Button } from "@/components/ui/button.tsx";
import useAuth from "@/hooks/useAuth.ts";
import { UserCog2Icon } from "lucide-react";
import "./Header.css";

function Header() {
  const { isLoggedIn, logout } = useAuth();

  const handleLogout = () => {
    logout();
  };

  const handleProfileEdit = () => {
    console.log("Profile edited");
  };

  return (
    <header className="App-header">
      <img className="App-logo" src={dropletLogo} alt="droplet logo" />
      <div className="App-header-menu">
        {
          isLoggedIn &&
          <>
            <Button type="button" variant="secondary"
                    onClick={handleProfileEdit}>
              <UserCog2Icon />
              Profile
            </Button>
            <Button type="button" variant="destructive" onClick={handleLogout}>
              Logout
            </Button>
          </>
        }
        <Button type="button" variant="link" asChild>
          <a href="https://github.com/priyakdey/droplet"
             rel="noreferrer noopener"
             target="_blank">
            <img className="github-logo" src={githubLogo} alt="github logo" />
          </a>
        </Button>
      </div>
    </header>
  );
}

export default Header;