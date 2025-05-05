import * as React from "react";
import { createContext, useState } from "react";

interface Profile {
  id: number;
  name: string;
}

interface ProfileContextType {
  profile: Profile | null;
  setProfile: (profile: Profile) => void;
}

// eslint-disable-next-line react-refresh/only-export-components
export const ProfileContext = createContext<ProfileContextType | null>(null);

export function ProfileProvider({ children }: { children: React.ReactNode }) {
  const [ profile, setProfile ] = useState<Profile | null>(null);

  return (
    <ProfileContext.Provider value={{ profile, setProfile }}>
      {children}
    </ProfileContext.Provider>
  );
}