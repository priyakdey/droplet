import { useEffect } from "react";

function HomePage() {

  useEffect(() => {
    fetch("http://localhost:8080/me", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      credentials: "include"
    })
      .then(response => response.text())
      .catch(error => console.log(error));
  });

  return (
    <div>
      Home Page
    </div>
  );
}

export default HomePage;