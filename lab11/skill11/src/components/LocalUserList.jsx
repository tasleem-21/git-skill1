import { useEffect, useState } from 'react';

function LocalUserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const controller = new AbortController();

    const loadUsers = async () => {
      setLoading(true);
      setError('');

      try {
        const response = await fetch('/users.json', { signal: controller.signal });

        if (!response.ok) {
          throw new Error(`Unable to load local users (${response.status})`);
        }

        const data = await response.json();
        setUsers(Array.isArray(data) ? data : []);
      } catch (fetchError) {
        if (fetchError.name !== 'AbortError') {
          setError(fetchError.message || 'Failed to load local users.');
        }
      } finally {
        if (!controller.signal.aborted) {
          setLoading(false);
        }
      }
    };

    loadUsers();

    return () => controller.abort();
  }, []);

  return (
    <section className="panel">
      <div className="section-heading compact">
        <a className="back-link" href="#dashboard">
          ← Back to Dashboard
        </a>
        <div>
          <p className="section-kicker">Part A</p>
          <h2>Local Users</h2>
          <p className="section-copy">
            Data source: public/users.json loaded with fetch().
          </p>
        </div>
      </div>

      {loading ? <p className="status-message">Loading local user records...</p> : null}
      {error ? <p className="status-message error">{error}</p> : null}

      {!loading && !error ? (
        <div className="user-grid">
          {users.map((user, index) => (
            <article className="record-card" key={`${user.email}-${index}`}>
              <h3>{user.name}</h3>
              <p>{user.email}</p>
              <p>{user.phone}</p>
            </article>
          ))}
        </div>
      ) : null}
    </section>
  );
}

export default LocalUserList;
