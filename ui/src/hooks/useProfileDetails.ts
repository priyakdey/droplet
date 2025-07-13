import { ProfileDetailsContext } from "@/context/ProfileDetailsContext.tsx";
import { useContext } from "react";

const useProfileDetails = () => useContext(ProfileDetailsContext)!;
export default useProfileDetails;