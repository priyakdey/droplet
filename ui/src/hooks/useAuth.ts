import { AuthContext } from "@/context/AuthContext.tsx";
import { useContext } from "react";

export const useAuth = () => useContext(AuthContext)!;