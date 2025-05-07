import { buildDirectoryTree } from "@/common/util.ts";
import BreadcrumbTrail from "@/components/breadcrumb/BreadcrumbTrail.tsx";
import Footer from "@/components/footer/Footer.tsx";
import Header from "@/components/header/Header.tsx";
import Layout from "@/components/layout/Layout.tsx";
import { getAllDirectories } from "@/services/directory.service.ts";
import { Directory } from "@/types/directory-ui.types.ts";
import { useEffect, useState } from "react";
import { toast } from "sonner";
import "./HomePage.css";
import { useLocation } from "react-router-dom";


function HomePage() {
  const [ directoryTree, setDirectoryTree ] = useState<Directory[]>([]);

  const location = useLocation();

  useEffect(() => {
    getAllDirectories()
      .then(body => {
        const tree = buildDirectoryTree(body.directories);
        setDirectoryTree(tree);
      })
      .catch(err => {
        console.log(err);
        toast.error("Failed to load directoryTree", { duration: 5000 });
      });
  }, []);

  const crumbs = location.pathname
    .split("/")
    .filter(Boolean)
    .map((segment, idx, arr) => {
      const href = "/" + arr.slice(0, idx + 1).join("/");
      const label = segment.charAt(0).toUpperCase() + segment.slice(1);
      return { label, href };
    });

  return (
    <div className="page-container">
      <Header />
      <div className="sidebar-fixed">
        <Layout directoryTree={directoryTree} />
      </div>
      <div className="right-panel">
        <div className="breadcrumb-container">
          <BreadcrumbTrail crumbs={crumbs} />
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