import { useEffect, useState } from 'react';
import Dashboard from './components/Dashboard';
import FakePostList from './components/FakePostList';
import LocalUserList from './components/LocalUserList';
import UserList from './components/UserList';

const VALID_VIEWS = new Set(['dashboard', 'local-users', 'users-api', 'fake-api-posts']);

const VIEW_CONFIG = {
  dashboard: {
    title: 'My Dashboard',
    subtitle: 'Navigate to local JSON, public API, and fake API examples with a single click.',
  },
  'local-users': {
    title: 'Local JSON Users',
    subtitle: 'Data is loaded from users.json inside the public folder with fetch().',
  },
  'users-api': {
    title: 'JSONPlaceholder Users',
    subtitle: 'This section fetches live user data from a public API endpoint.',
  },
  'fake-api-posts': {
    title: 'Fake API Posts',
    subtitle: 'This section uses Axios to load fake post data and filter it by userId.',
  },
};

function resolveView(hash) {
  const value = hash.replace('#', '').trim();
  return VALID_VIEWS.has(value) ? value : 'dashboard';
}

function App() {
  const [view, setView] = useState(() => resolveView(window.location.hash));

  useEffect(() => {
    const syncView = () => {
      const nextView = resolveView(window.location.hash);
      setView(nextView);

      if (window.location.hash !== `#${nextView}`) {
        window.history.replaceState(null, '', `#${nextView}`);
      }
    };

    syncView();
    window.addEventListener('hashchange', syncView);

    return () => window.removeEventListener('hashchange', syncView);
  }, []);

  const content = {
    dashboard: <Dashboard />,
    'local-users': <LocalUserList />,
    'users-api': <UserList />,
    'fake-api-posts': <FakePostList />,
  }[view];

  const activeView = VIEW_CONFIG[view];

  return (
    <div className="app-shell">
      <header className="hero">
        <p className="eyebrow">FSAD Skill 11</p>
        <h1>{activeView.title}</h1>
        <p className="hero-copy">{activeView.subtitle}</p>
      </header>

      <main className="content-area">{content}</main>
    </div>
  );
}

export default App;
