import { useEffect, useState } from 'react';
import axios from 'axios';

const POSTS_URL = 'https://dummyjson.com/posts';

function FakePostList() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [selectedUserId, setSelectedUserId] = useState('all');

  const loadPosts = async () => {
    setLoading(true);
    setError('');

    try {
      const response = await axios.get(POSTS_URL);
      setPosts(Array.isArray(response.data?.posts) ? response.data.posts : []);
    } catch (axiosError) {
      setError(axiosError.message || 'Failed to fetch posts from the fake API.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPosts();
  }, []);

  const uniqueUserIds = Array.from(new Set(posts.map((post) => post.userId))).sort((a, b) => a - b);
  const filteredPosts =
    selectedUserId === 'all'
      ? posts
      : posts.filter((post) => String(post.userId) === selectedUserId);

  return (
    <section className="panel">
      <div className="section-heading compact">
        <a className="back-link" href="#dashboard">
          ← Back to Dashboard
        </a>
        <div>
          <p className="section-kicker">Part C</p>
          <h2>Fake API Posts</h2>
          <p className="section-copy">
            Data source: dummyjson posts loaded with Axios, plus a userId filter and refresh button.
          </p>
        </div>
      </div>

      <div className="toolbar">
        <label className="filter-group" htmlFor="userIdFilter">
          <span>Filter by userId</span>
          <select
            id="userIdFilter"
            className="filter-select"
            value={selectedUserId}
            onChange={(event) => setSelectedUserId(event.target.value)}
          >
            <option value="all">All users</option>
            {uniqueUserIds.map((userId) => (
              <option key={userId} value={String(userId)}>
                userId {userId}
              </option>
            ))}
          </select>
        </label>

        <button className="refresh-button" type="button" onClick={loadPosts}>
          Refresh
        </button>
      </div>

      {loading ? <p className="status-message">Loading fake API posts...</p> : null}
      {error ? <p className="status-message error">{error}</p> : null}

      {!loading && !error ? (
        <div className="post-list">
          {filteredPosts.map((post) => (
            <article className="post-card" key={post.id}>
              <div className="post-meta">
                <span>Post #{post.id}</span>
                <span>userId {post.userId}</span>
              </div>
              <h3>{post.title}</h3>
              <p>{post.body}</p>
              <div className="tag-row">
                {Array.isArray(post.tags)
                  ? post.tags.map((tag) => (
                      <span className="tag" key={`${post.id}-${tag}`}>
                        {tag}
                      </span>
                    ))
                  : null}
              </div>
            </article>
          ))}
        </div>
      ) : null}
    </section>
  );
}

export default FakePostList;
