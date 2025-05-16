import * as React from "react";
import { createContext, useState } from "react";

interface ProfileContextType {
  accountId: number | null;
  profileId: number | null;
  homeDirId: string | null;
  name: string | null;
  timezone: string | null;
  setProfileDetails: (accountId: number, profileId: number, homeDirId: string, name: string, timezone: string) => void;
  updateProfileDetails: (name: string, timezone: string) => void;
}

// eslint-disable-next-line react-refresh/only-export-components
export const ProfileContext = createContext<ProfileContextType | null>(null);

interface ProfileProviderPropsType {
  children: React.ReactNode;
}

function ProfileProvider({ children }: ProfileProviderPropsType) {
  const [ accountId, setAccountId ] = useState<number | null>(null);
  const [ profileId, setProfileId ] = useState<number | null>(null);
  const [ homeDirId, setHomeDirId ] = useState<string | null>(null);
  const [ name, setName ] = useState<string | null>(null);
  const [ timezone, setTimezone ] = useState<string | null>(null);

  const setProfileDetails = (accountId: number, profileId: number,
                             homeDirId: string, name: string, timezone: string) => {
    setAccountId(accountId);
    setProfileId(profileId);
    setHomeDirId(homeDirId);
    setName(name);
    setTimezone(timezone);
  };

  const updateProfileDetails = (name: string, timezone: string) => {
    setName(name);
    setTimezone(timezone);
  };

  return (
    <ProfileContext.Provider value={{
      accountId,
      profileId,
      homeDirId,
      name,
      timezone,
      setProfileDetails,
      updateProfileDetails
    }}>
      {children}
    </ProfileContext.Provider>
  );
}

export default ProfileProvider;