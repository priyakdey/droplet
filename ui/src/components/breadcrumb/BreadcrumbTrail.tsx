import {
  Breadcrumb,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator
} from "@/components/ui/breadcrumb.tsx";

export interface Crumb {
  id: string;
  label: string;
  href: string;
}

interface BreadcrumbTrailProps {
  crumbs: Crumb[];
  setActiveDirId: (dirId: string) => void;
}

function BreadcrumbTrail({ crumbs, setActiveDirId }: BreadcrumbTrailProps) {
  return (
    <Breadcrumb>
      <BreadcrumbList>
        {
          crumbs.map((crumb, idx) => (
            <span key={crumb.href} className="flex items-center gap-3">
              <BreadcrumbLink href={`/${crumb.href}`}
                              onClick={(e) => {
                                e.preventDefault();
                                setActiveDirId(crumb.id);
                              }}>
                {crumb.label}
              </BreadcrumbLink>
              {idx < crumbs.length - 1 && <BreadcrumbSeparator />}
            </span>
          ))
        }
      </BreadcrumbList>
    </Breadcrumb>
  );
}

export default BreadcrumbTrail;