const tiles = [
  {
    href: '#local-users',
    title: 'Local Users',
    description: 'Load 5 to 6 users from a users.json file in the public folder using fetch().',
  },
  {
    href: '#users-api',
    title: 'Users API',
    description: 'Fetch user records from JSONPlaceholder and render the response statefully.',
  },
  {
    href: '#fake-api-posts',
    title: 'Fake API Posts',
    description: 'Use Axios with dummyjson posts, refresh the feed, and filter by userId.',
  },
];

function Dashboard() {
  return (
    <section className="dashboard-panel">
      <div className="section-heading">
        <p className="section-kicker">Home</p>
        <h2>Choose a data source</h2>
        <p className="section-copy">
          Each card opens a different React data-fetching example so you can compare local JSON,
          fetch(), and Axios side by side.
        </p>
      </div>

      <div className="dashboard-grid">
        {tiles.map((tile) => (
          <a key={tile.title} className="feature-card" href={tile.href}>
            <span className="feature-pill">Open</span>
            <h3>{tile.title}</h3>
            <p>{tile.description}</p>
          </a>
        ))}
      </div>
    </section>
  );
}

export default Dashboard;
