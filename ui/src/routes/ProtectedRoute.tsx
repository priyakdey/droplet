import { useAuth } from "@/hooks/useAuth.ts";
import { JSX } from "react";
import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }: { children: JSX.Element }) {
  const auth = useAuth();

  if (!auth.isLoggedIn) {
    return <Navigate to="/" replace/>;
  }

  return children;
}

export default ProtectedRoute;