import { Button } from "@/components/ui/button.tsx";

import "./SocialAuthButton.css";

interface SocialAuthButtonProps {
  logo: string;
  text: string;
  onClick?: () => void;
}

function SocialAuthButton({ logo, text }: SocialAuthButtonProps) {
  return (
    <Button variant="default" type="button"
            className="SocialAuthButton">
      <img src={logo} alt="google logo" />
      {text}
    </Button>
  );
}

export default SocialAuthButton;