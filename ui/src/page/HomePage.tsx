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
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import "./HomePage.css";


function HomePage() {
  const [ directoryTree, setDirectoryTree ] = useState<Directory[]>([]);
  const [ idToDirectory, setIdToDirectory ] = useState<Map<string, Directory>>(new Map<string, Directory>());
  const [ idToParentDirectory, setIdToParentDirectory ] = useState<Map<string, Directory>>(new Map<string, Directory>());
  const [ activeDirId, setActiveDirId ] = useState<string | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    getAllDirectories()
      .then(body => {
        const directoryDtos = body.directories;
        const { root, idToDirectoryMap } = buildDirectoryTree(directoryDtos);
        setDirectoryTree(root);
        setIdToDirectory(idToDirectoryMap);
        setActiveDirId(root[0].id);
        setIdToParentDirectory(buildParentMap(idToDirectoryMap));
      })
      .catch(err => {
        console.error(err);
        toast.error("Failed to load directoryTree", { duration: 5000 });
      });
  }, []);

  const selectCurrDir: (dirId: string) => void = (dirId) => {
    setActiveDirId(dirId);
    const dir = idToDirectory.get(dirId);
    if (dir) {
      navigate(`/${dir.url}`);
    }
  };

  const generateCrumbs: () => Crumb[] = () => {
    let dirId = activeDirId!;
    if (!dirId) {
      return [];
    }

    const crumbs: Crumb[] = [];

    while (true) {
      const dir = idToDirectory.get(dirId)!;
      crumbs.unshift({
        id: dirId,
        label: dir.name,
        href: dir.url.toLowerCase()
      });
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
        <Layout directoryTree={directoryTree} activeDirId={activeDirId!}
                setActiveDirId={selectCurrDir} />
      </div>
      <div className="right-panel">
        <div className="breadcrumb-container">
          <BreadcrumbTrail crumbs={generateCrumbs()}
                           setActiveDirId={selectCurrDir} />
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