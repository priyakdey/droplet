### Authentication
- [X] Landing Page
- [X] Login with Google (OAuth)
- [X] Login with GitHub
- [ ] Login with Facebook
- [X] Secure cookie-based authentication (HttpOnly + SameSite=strict)
- [X] Profile API (fetch user info post login)
- [X] Store & edit user preferences (Timezone, Display Name, Avatar)

---

### Layout + Navigation
- [ ] Sidebar integration with collapsible folders
- [ ] Display tree hierarchy of directories in sidebar
- [ ] Breadcrumbs for current directory path
- [ ] Dynamic route based on selected directory (e.g., `/folder/:id`)
- [ ] Sync sidebar, breadcrumb, and URL (source of truth)

---

### Folder & File Management
- [ ] Define MongoDB data model for folders and files
- [ ] Create new directory
- [ ] Upload a file to selected directory
- [ ] Delete a file
- [ ] Show list of files and folders (Card/Tile or List)
- [ ] (Optional) File thumbnails by type (PDF, image, doc) — static icons
- [ ] Upload progress indicator (UI feedback)

---

### File Details & Sharing
- [ ] View file metadata (name, size, type, createdAt, etc.)
- [ ] Generate public share link
- [ ] Share file with an email (public or restricted access)

---

### For later
- [ ] Avatar fallback strategy
- [ ] Retry + observability pattern for blob downloads
- [ ] 
