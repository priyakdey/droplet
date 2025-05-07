import { Button as ShadcnButton } from "@/components/ui/button.tsx";
import * as React from "react";

export interface ButtonProps {
  className?: string;
  type: "submit" | "button" | "reset";
  variant?: "link" | "default" | "destructive" | "outline" | "secondary" | "ghost" | null | undefined;
  onClick?: () => void;
  children?: React.ReactNode;
}

function Button({
                  className,
                  type,
                  variant,
                  onClick,
                  children
                }: ButtonProps) {
  return (
    <ShadcnButton type={type} variant={variant}
                  className={className} onClick={onClick}>
      {children}
    </ShadcnButton>
  );
}

export default Button;