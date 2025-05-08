import { Button as ShadcnButton } from "@/components/ui/button.tsx";
import * as React from "react";


export type ButtonProps = React.ComponentPropsWithRef<typeof ShadcnButton>

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  ({
     className,
     type = "button",
     variant,
     onClick,
     children,
     ...props
   }, ref) => {
    return (
      <ShadcnButton ref={ref} type={type} variant={variant}
                    className={className} onClick={onClick} {...props}>
        {children}
      </ShadcnButton>
    );
  }
);

Button.displayName = "Button";

export default Button;