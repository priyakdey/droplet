import { Button as ShadcnButton } from "@/components/ui/button.tsx";

export interface ButtonProps {
  className?: string;
  type: "submit" | "button" | "reset";
  innerHtmlText: string;
  variant?: "link" | "default" | "destructive" | "outline" | "secondary" | "ghost" | null | undefined;
  onClick?: () => void;
}

function Button({
                  className,
                  type,
                  innerHtmlText,
                  variant,
                  onClick
                }: ButtonProps) {
  return (
    <ShadcnButton type={type} variant={variant}
                  className={className} onClick={onClick}>
      {innerHtmlText}
    </ShadcnButton>
  );
}

export default Button;