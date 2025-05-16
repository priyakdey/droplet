import useAuth from "@/hooks/useAuth.ts";
import * as React from "react";
import { Navigate } from "react-router-dom";

interface ProtectedRoutePropsType {
  children: React.ReactNode;
}

function ProtectedRoute({ children }: ProtectedRoutePropsType) {
  const { isLoggedIn, isLoading } = useAuth();

  if (isLoading) {
    return <p>Loading</p>;
  } else if (!isLoggedIn) {
    return <Navigate to="/" replace />;
  } else {
    return children;
  }

}

export default ProtectedRoute;