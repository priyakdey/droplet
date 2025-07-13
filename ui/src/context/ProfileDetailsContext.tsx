import useAuth from "@/hooks/useAuth.ts";
import { getProfileDetails } from "@/service/profileService.ts";
import type { AppError } from "@/types/error.ts";
import type { ProfileDetails } from "@/types/ui-types.ts";
import * as React from "react";
import { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router";

interface ProfileDetailsContextType {
  profileDetails: ProfileDetails;
  setProfileDetails: (profile: ProfileDetails) => void;
}

interface ProfileDetailsProviderPropsType {
  children: React.ReactNode;
}

const ProfileDetailsContext = createContext<ProfileDetailsContextType | null>(null);

function ProfileDetailsProvider({ children }: ProfileDetailsProviderPropsType) {
  const [ profileDetails, setProfileDetails ] = useState<ProfileDetails | null>(null);
  const { setIsLoading, setIsAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    setIsLoading(true);

    getProfileDetails()
      .then(data => {
        setProfileDetails(data);
        setIsAuthenticated(true);
        setIsLoading(false);
        navigate("/home");
      })
      .catch(err => {
        const { status } = err as AppError;

        if (status === 401) {
          setIsAuthenticated(false);
          navigate("/", { replace: true });
        }
      })
      .finally(() => setIsLoading(false));
  }, []);

  return (
    <ProfileDetailsContext.Provider
      value={{ profileDetails: profileDetails!, setProfileDetails }}>
      {children}
    </ProfileDetailsContext.Provider>
  );
}

export { ProfileDetailsProvider, ProfileDetailsContext };