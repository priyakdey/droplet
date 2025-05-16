import useAuth from "@/hooks/useAuth";
import useExplorer from "@/hooks/useExplorer.ts";
import AppLayout from "@/layout/AppLayout.tsx";
import { getAllDirectories } from "@/service/inodeService.ts";
import { useEffect } from "react";
import { toast } from "sonner";

function HomePage() {
  const { token } = useAuth();
  const { isLoading, buildTree } = useExplorer();

  useEffect(() => {
    if (!token) return;

    getAllDirectories(token!)
      .then(data => {
        buildTree(data.directories);
      })
      .catch(error => {
        const description = typeof error.cause === "string"
          ? error.cause : "An unknown error occurred";
        toast.error(error.message, { description: description });
      });
  }, [ token ]);

  if (isLoading) return null;

  return (
    <AppLayout className="HomePage-app-layout">
      HomePage
    </AppLayout>
  );
}

export default HomePage;