import faceBookLogo from "@/assets/Facebook_Logo_Primary.png";
import githubLogoDark from "@/assets/github-mark.png";
import googleLogo from "@/assets/google.webp";
import SocialAuthButton from "@/components/button/SocialAuthButton.tsx";
import FeatureCard from "@/components/card/FeatureCard.tsx";
import { FolderClosed, Link, ShieldCheck } from "lucide-react";
import { motion } from "motion/react";

import "./WelcomePage.css";

function WelcomePage() {

  function handleGoogleLogin() {
    window.location.href = "http://localhost:8080/api/auth/google/login";
  }

  return (
    <div className="WelcomePage">
      <header className="WelcomePage-header">
      </header>
      <main className="WelcomePage-main">
        <img src="/droplet.png" alt="droplet logo"
             className="WelcomePage-logo" />
        <div className="WelcomePage-content-container">
          <motion.div initial={{ x: -500, y: 0, opacity: 0 }}
                      animate={{ x: 0, y: 0, opacity: 1 }}
                      transition={{ duration: 0.6, ease: "easeIn" }}
          >
            <div className="WelcomePage-content">
              <div>
                <h1 className="WelcomePage-content-header">
                  Your digital junk drawer
                </h1>
              </div>
              <div>
                <p className="WelcomePage-content-subtext">
                  Tired of messy desktops and lost files? With our platform, you
                  can upload any file, organize your documents, photos, and more
                  into tidy folders, and share them instantly with friends or
                  colleagues. Enjoy a seamless, secure, and clutter-free storage
                  experience - so you can always find what you need, when you
                  need
                  it.
                </p>
              </div>
              <div className="WelcomePage-features">
                <div>
                  <FeatureCard logo={ShieldCheck} header="Secure Storage"
                               subtext="Your files are safely stored with end to end encryption" />
                </div>
                <div>
                  <FeatureCard logo={FolderClosed} header="File Organization"
                               subtext="Create folders to keep your files organized" />
                </div>
                <div>
                  <FeatureCard logo={Link} header="Easy Sharing"
                               subtext="Share files with anyone using public urls" />
                </div>
              </div>
            </div>
          </motion.div>
          <motion.div initial={{ x: 500, y: 0, opacity: 1 }}
                      animate={{ x: 0, y: 0, opacity: 1 }}
                      transition={{ duration: 0.6, ease: "easeIn" }}
          >
            <div className="WelcomePage-cta">
              <div>
                <SocialAuthButton logo={googleLogo} text={"Login With Google"}
                                  onClick={handleGoogleLogin} />
              </div>
              <div>
                <SocialAuthButton logo={githubLogoDark}
                                  text={"Login With Github"}
                                  onClick={handleGoogleLogin} />
              </div>
              <div>
                <SocialAuthButton logo={faceBookLogo}
                                  text={"Login With Facebook"}
                                  onClick={handleGoogleLogin} />
              </div>
            </div>
          </motion.div>
        </div>
      </main>
      <footer className="WelcomePage-footer">
      </footer>
    </div>
  );
}

export default WelcomePage;