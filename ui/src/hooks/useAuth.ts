import { AuthContext } from "@/context/AuthContext.tsx";
import { useContext } from "react";

const useAuth = () => useContext(AuthContext)!;
export default useAuth;