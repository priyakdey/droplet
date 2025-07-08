import type { LucideProps } from "lucide-react";
import * as react from "react";

export type LucideLogo = react.ForwardRefExoticComponent<Omit<LucideProps, "ref"> & react.RefAttributes<SVGSVGElement>>;