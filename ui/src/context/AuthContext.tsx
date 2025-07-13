import * as React from "react";
import { createContext, useState } from "react";

interface AuthContextType {
  isLoading: boolean;
  setIsLoading: (what: boolean) => void;
  isAuthenticated: boolean;
  setIsAuthenticated: (what: boolean) => void;
  logout: () => void;
}

interface AuthProviderPropsType {
  children: React.ReactNode;
}

const AuthContext = createContext<AuthContextType | null>(null);


function AuthProvider({ children }: AuthProviderPropsType) {
  const [ isLoading, setIsLoading ] = useState<boolean>(true);
  const [ isAuthenticated, setIsAuthenticated ] = useState<boolean>(false);

  const logout = async () => {
    console.log("TODO! Bling");
  };

  return (
    <AuthContext.Provider value={{
      isLoading,
      setIsLoading,
      isAuthenticated,
      setIsAuthenticated,
      logout
    }}>
      {children}
    </AuthContext.Provider>
  );
}

export { AuthProvider, AuthContext };