# Angular Quiz

This is a demo project built with **Angular 17** and **Angular Material**.  
It was created as part of a coding exercise to demonstrate clean architecture, responsive UI, and use of Angular features such as interceptors, services, and reactive forms.

---

## ✨ Features

- **Paginated user list**
  - Fetches data from the [ReqRes API](https://reqres.in/api/users?page={page})
  - Displays user cards with avatar, name, and ID
  - Horizontally centered and responsive
- **User detail page**
  - Clicking a card navigates to a dedicated page with details
  - Includes a back button
- **Instant search by ID**
  - Input field in the header
  - Shows preview card if the user exists
  - Shows a “No user found” message for invalid IDs (outside 1–12)
- **Interceptors**
  - `auth.interceptor.ts` → adds header `x-api-key: reqres-free-v1`
  - `loading.interceptor.ts` → shows a progress bar while requests are pending
  - `cache.interceptor.ts` → caches responses to avoid duplicate requests
- **Loading bar**
  - Material progress bar appears during API calls
  - Minimum visible duration so it’s noticeable
  
---

## 🔧 How to Run

1. Install dependencies:
   ```bash
   npm install

2. Start the development server:
   ```bash
   npm start
   
3. Build for production:
  ```bash
  npm run build