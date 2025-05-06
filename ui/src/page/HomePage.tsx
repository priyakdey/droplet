import Footer from "@/components/footer/Footer.tsx";
import Header from "@/components/header/Header.tsx";
import "./HomePage.css";

function HomePage() {
  return (
    <div className="page-container">
      <Header />
      <main className="main-content">
        HomePage
      </main>
      <Footer />
    </div>
  );
}

export default HomePage;