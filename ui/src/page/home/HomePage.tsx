import useProfileDetails from "@/hooks/useProfileDetails.ts";
import { toast } from "sonner";

function HomePage() {
  const { profileDetails } = useProfileDetails();

  return (
    <div>
      Hello, {profileDetails.name}
      <div>
        <img src={profileDetails.avatarUrl} alt="Profile picture of the user"
             onError={() => toast.warning("Could not load profile picture")}
        />
      </div>
    </div>
  );
}

export default HomePage;