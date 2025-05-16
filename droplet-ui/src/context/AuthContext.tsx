import * as React from "react";
import { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

interface AuthContextType {
  token: string | null;
  isLoggedIn: boolean;
  isLoading: boolean;
  login: (token: string) => void;
  logout: () => void;
}

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext<AuthContextType | null>(null);

interface AuthProviderPropsType {
  children: React.ReactNode;
}

function AuthProvider({ children }: AuthProviderPropsType) {
  const [ token, setToken ] = useState<string | null>(null);
  const [ isLoading, setIsLoading ] = useState<boolean>(true);

  const navigate = useNavigate();

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    // TODO: force logout if not present
    if (storedToken) {
      setToken(storedToken);
    }
    setIsLoading(false);
  }, []);

  const login = (token: string) => {
    setToken(token);
    localStorage.setItem("token", token);
  };

  const logout = () => {
    setToken(null);
    localStorage.clear();
    navigate("/", { replace: true });
  };

  const isLoggedIn = token !== null;

  return (
    <AuthContext.Provider
      value={{ token, isLoggedIn, isLoading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export default AuthProvider;