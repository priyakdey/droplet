import dropletSvgUrl from "@/assets/droplet.svg";
import Button from "@/components/button/Button.tsx";
import { useAuth } from "@/hooks/useAuth.ts";
import { useProfile } from "@/hooks/useProfile";
import "./Header.css";

function Header() {
  const { isLoggedIn } = useAuth();
  const { profile } = useProfile();

  return (
    <header className="header-container">
      <img src={dropletSvgUrl} alt="Droplet logo" />
      <div
        className="container flex flex-row justify-around items-center w-1/4 gap-0">
        {isLoggedIn && <p>{profile?.name}</p>}
        {isLoggedIn &&
          <Button className="h-8 w-30" type="button" innerHtmlText="Logout"
                  variant="destructive" />}
      </div>
    </header>
  );
}

export default Header;