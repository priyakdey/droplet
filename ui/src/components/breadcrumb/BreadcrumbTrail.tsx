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
}

function BreadcrumbTrail({ crumbs }: BreadcrumbTrailProps) {
  return (
    <Breadcrumb>
      <BreadcrumbList>
        {
          crumbs.map((crumb, idx) => (
            <span key={crumb.href} className="flex items-center gap-3">
              <BreadcrumbLink href={crumb.href}>{crumb.label}</BreadcrumbLink>
              {idx < crumbs.length - 1 && <BreadcrumbSeparator />}
            </span>
          ))
        }
      </BreadcrumbList>
    </Breadcrumb>
  );
}

export default BreadcrumbTrail;