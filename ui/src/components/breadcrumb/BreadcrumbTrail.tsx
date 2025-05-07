import {
  Breadcrumb,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator
} from "@/components/ui/breadcrumb.tsx";

export interface Crumbs {
  label: string;
  href: string;
}

interface BreadcrumbTrailProps {
  crumbs: Crumbs[];
}

function BreadcrumbTrail({ crumbs }: BreadcrumbTrailProps) {
  return (
    <Breadcrumb>
      <BreadcrumbList>
        {
          crumbs.map((crumb, idx) => (
            <span key={crumb.href} className="flex items-center">
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