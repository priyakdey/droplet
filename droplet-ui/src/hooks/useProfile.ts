import { ProfileContext } from "@/context/ProfileContext";
import { useContext } from "react";

const useProfile = () => useContext(ProfileContext)!;

export default useProfile;