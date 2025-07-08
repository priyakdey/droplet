import type { LucideLogo } from "@/types/ui-types.ts";

import "./FeatureCard.css"

function FeatureCard({ logo, header, subtext }: {
  logo: LucideLogo,
  header: string,
  subtext: string
}) {
  const Logo = logo;
  return (
    <div className="FeatureCard">
      <Logo className="FeatureCard-logo" />
      <div className="FeatureCard-header">
        {header}
      </div>
      <div className="FeatureCard-subtext">
        {subtext}
      </div>
    </div>
  );
}

export default FeatureCard;