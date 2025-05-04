import tailwindcss from "@tailwindcss/vite";
import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

// ✅ Modern ESM-safe way to alias paths
export default defineConfig({
  plugins: [ react(), tailwindcss() ],
  resolve: {
    alias: {
      "@": new URL("./src", import.meta.url).pathname
    }
  }
});
