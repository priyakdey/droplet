import { ExplorerContext } from "@/context/ExplorerContext.tsx";
import { useContext } from "react";

const useExplorer = () => {
  const context = useContext(ExplorerContext)!;
  if (!context) throw new Error("useExplorer must be used within ExplorerProvider with initialized state");
  return context;
};

export default useExplorer;