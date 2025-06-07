# X Client

X Client is a modern social networking web application built with React, Redux Toolkit, React Query, and Tailwind CSS. It provides features similar to popular social platforms, including posting, liking, following, bookmarking, notifications, and user profile management.

## Features

- User authentication (register, login, logout)
- Create, edit, and delete posts with images
- Like, share, and bookmark posts
- Comment on posts
- Follow and unfollow users
- View suggested users to follow
- Real-time notifications
- Responsive and modern UI with Tailwind CSS and DaisyUI
- Persistent user sessions with Redux Persist

## Tech Stack

- [React](https://react.dev/)
- [Redux Toolkit](https://redux-toolkit.js.org/)
- [React Query](https://tanstack.com/query/latest)
- [Tailwind CSS](https://tailwindcss.com/)
- [DaisyUI](https://daisyui.com/)
- [Axios](https://axios-http.com/)
- [Vite](https://vitejs.dev/)

## Getting Started

### Prerequisites

- Node.js (v16 or higher)
- npm

### Installation

1. Clone the repository:

```sh
git clone https://github.com/Hai1205/X_Client.git
cd X_CLient
```

2. Install dependencies:

```sh
npm install
```

3. Create a .env file in the root directory and add your server URL:

VITE_SERVER_URL=

4. Start the development server:

```sh
npm run dev
```

Open [http://localhost:7070](http://localhost:7070) in your browser.

## Project Structure
- src/ components/
- Reusable UI components hooks/
- Custom React hooks pages/
- Application pages (Home, Profile, etc.) redux/
- Redux store and slices utils/
- Utility functions and API services public/
- Static assets

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

**Author:** [Hai1205](https://github.com/hai1205)