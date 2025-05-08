import { Progress } from "@/components/ui/progress.tsx";
import { useAuth } from "@/hooks/useAuth.ts";
import { JSX } from "react";
import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }: { children: JSX.Element }) {
  const { isLoggedIn, isLoading } = useAuth();

  if (isLoading) return <Progress value={33} />;
  if (!isLoggedIn) return <Navigate to="/" replace />;
  return children;
}

export default ProtectedRoute;