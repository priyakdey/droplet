import Footer from "@/components/footer/Footer.tsx";
import Header from "@/components/header/Header.tsx";
import Layout from "@/components/layout/Layout.tsx";
import "./HomePage.css";


function HomePage() {
  return (
    <div className="page-container">
      <Header />
      <main className="main-content">
        <aside>
          <Layout />
        </aside>
        <section>
          <p>Hello</p>
        </section>
      </main>
      <Footer />
    </div>
  );
}

export default HomePage;