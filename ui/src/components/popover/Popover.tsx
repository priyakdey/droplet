import Button from "@/components/button/Button.tsx";
import {
  Popover as ShadcnPopover,
  PopoverContent,
  PopoverTrigger
} from "@/components/ui/popover";
import { PlusCircle } from "lucide-react";
import * as React from "react";
import { useState } from "react";
import "./Popover.css";

interface PopoverPropsType {
  children?: ((onClose: () => void) => React.ReactNode) | React.ReactNode;
}

function Popover({ children }: PopoverPropsType) {
  const [ isOpen, setIsOpen ] = useState<boolean>(false);

  const handleClose = () => setIsOpen(false);

  return (
    <ShadcnPopover open={isOpen} onOpenChange={setIsOpen}>
      <PopoverTrigger>
        <Button type="button" variant="default" className="popover-btn">
          <PlusCircle />
          <span className="popover-new-dir-text">New Directory</span>
        </Button>
      </PopoverTrigger>
      <PopoverContent>
        {typeof children === "function" ? children(handleClose) : children}
      </PopoverContent>
    </ShadcnPopover>
  );
}

export default Popover;