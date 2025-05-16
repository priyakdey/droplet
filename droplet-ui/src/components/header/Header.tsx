import dropletLogo from "@/assets/droplet-logo.svg";
import githubLogo from "@/assets/github-mark.svg";
import EditProfileForm from "@/components/form/editprofile/EditProfileForm.tsx";
import { Button } from "@/components/ui/button.tsx";
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetDescription,
  SheetFooter,
  SheetHeader,
  SheetTitle,
  SheetTrigger
} from "@/components/ui/sheet.tsx";
import useAuth from "@/hooks/useAuth.ts";
import { UserCog2Icon } from "lucide-react";
import "./Header.css";
import { useRef } from "react";

function Header() {
  const { isLoggedIn, logout } = useAuth();

  const closeRef = useRef<HTMLButtonElement>(null);

  const handleLogout = () => {
    logout();
  };

  return (
    <header className="App-header">
      <img className="App-logo" src={dropletLogo} alt="droplet logo" />
      <div className="App-header-menu">
        {
          isLoggedIn &&
          <>
            <Sheet>
              <SheetTrigger asChild>
                <Button type="button" variant="secondary">
                  <UserCog2Icon />
                  Profile
                </Button>
              </SheetTrigger>
              <SheetContent>
                <SheetHeader>
                  <SheetTitle>Edit Profile</SheetTitle>
                  <SheetDescription>
                    Make changes to your profile here. Click save when you're
                    done.
                  </SheetDescription>
                </SheetHeader>
                <EditProfileForm closeSheet={() => closeRef.current?.click()} />
                <SheetFooter>
                  <SheetClose asChild>
                    <Button ref={closeRef} variant="ghost" />
                  </SheetClose>
                </SheetFooter>
              </SheetContent>
            </Sheet>

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