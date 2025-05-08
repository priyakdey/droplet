import { buildDirectoryTree, buildParentMap } from "@/common/util.ts";
import BreadcrumbTrail, {
  Crumb
} from "@/components/breadcrumb/BreadcrumbTrail.tsx";
import Footer from "@/components/footer/Footer.tsx";
import Header from "@/components/header/Header.tsx";
import Layout from "@/components/layout/Layout.tsx";
import { getAllDirectories } from "@/services/directory.service.ts";
import { Directory } from "@/types/directory-ui.types.ts";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { toast } from "sonner";
import "./HomePage.css";


function HomePage() {
  const [ directoryTree, setDirectoryTree ] = useState<Directory[]>([]);
  const [ idToDirectory, setIdToDirectory ] = useState<Map<string, Directory>>(new Map<string, Directory>());
  const [ idToParentDirectory, setIdToParentDirectory ] = useState<Map<string, Directory>>(new Map<string, Directory>());

  const [ currDir, setCurrDir ] = useState<Directory | null>(null);

  const location = useLocation();

  useEffect(() => {
    getAllDirectories()
      .then(body => {
        const directoryDtos = body.directories;
        // const idToDirMap = generateIdToDirMap(directoryDtos);
        const { root, idToDirectoryMap } = buildDirectoryTree(directoryDtos);
        setDirectoryTree(root);
        setIdToDirectory(idToDirectoryMap);
        setCurrDir(root[0]);
        setIdToParentDirectory(buildParentMap(idToDirectoryMap));
      })
      .catch(err => {
        console.error(err);
        toast.error("Failed to load directoryTree", { duration: 5000 });
      });
  }, []);

  const generateCrumbs: () => Crumb[] = () => {
    let dirId = currDir?.id;
    if (!dirId) {
      return [];
    }

    const crumbs: Crumb[] = [];

    while (true) {
      const dir = idToDirectory.get(dirId)!;
      crumbs.unshift({ id: dirId, label: dir.name, href: dir.url.toLowerCase() });
      const parent = idToParentDirectory.get(dirId);
      if (!parent) break;
      dirId = parent.id;
    }

    return crumbs;
  };

  return (
    <div className="page-container">
      <Header />
      <div className="sidebar-fixed">
        <Layout directoryTree={directoryTree} currDir={currDir}
                setCurrDir={setCurrDir} />
      </div>
      <div className="right-panel">
        <div className="breadcrumb-container">
          <BreadcrumbTrail crumbs={generateCrumbs()} />
        </div>
        <main className="main-content">
          Files go here
        </main>
      </div>
      <Footer />
    </div>
  );
}

export default HomePage;