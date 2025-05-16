import githubLogo from "@/assets/github-mark-white.svg";
import linkedinLogo from "@/assets/linkedin-logo.svg";
import { Button } from "@/components/ui/button.tsx";
import "./Footer.css";
import { GlobeIcon } from "lucide-react";


function Footer() {
  return (
    <footer className="App-footer">
      <div>
        &copy; 2025 &nbsp;
        <a href="https://priyakdey.com" rel="noreferrer noopener"
           target="_blank">
          <span className="App-footer-name">Priyak Dey</span>
        </a>
      </div>
      <div className="social-links-container">
        <Button type="button" variant="link" asChild>
          <a href="https://priyakdey.com"
             rel="noreferrer noopener"
             target="_blank"
             aria-label="Personal Website"
          >
            <GlobeIcon className="social-logo" />
          </a>
        </Button>
        <Button type="button" variant="link" asChild>
          <a href="https://www.linkedin.com/in/priyakdey/"
             rel="noreferrer noopener"
             target="_blank"
             aria-label="Linkedin Profile"
          >
            <img className="social-logo" src={linkedinLogo}
                 alt="linkedin logo" />
          </a>
        </Button>
        <Button type="button" variant="link" asChild>
          <a href="https://github.com/priyakdey/droplet"
             rel="noreferrer noopener"
             target="_blank"
             aria-label="Github Repository"
          >
            <img className="social-logo" src={githubLogo}
                 alt="github logo" />
          </a>
        </Button>
      </div>
    </footer>
  );
}

export default Footer;