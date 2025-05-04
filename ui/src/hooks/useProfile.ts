import { ProfileContext } from "@/context/ProfileContext.tsx";
import { useContext } from "react";

export const useProfile = () => useContext(ProfileContext)!;