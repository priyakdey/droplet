// import ButtonDestructive from "@/components/ui/ButtonDestructive.tsx";
import dropletSvgUrl from "@/assets/droplet.svg";
import "./Header.css";

function Header() {
  return (
    <header className="header-container">
      <img src={dropletSvgUrl} alt="Droplet logo" />
      {/* TODO: render this on login */}
      {/* <ButtonDestructive text="Logout" /> */}
    </header>
  );
}

export default Header;