import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();

  const handleLogin = () => {
    // no real auth, just redirect
    navigate("/home");
  };

  return (
    <div style={{
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      height: "100vh",
      background: "#f5f5f5"
    }}>
      <div style={{
        background: "white",
        padding: "30px",
        borderRadius: "10px",
        width: "300px",
        boxShadow: "0 0 10px rgba(0,0,0,0.2)"
      }}>
        <h2 style={{ textAlign: "center" }}>Login</h2>

        <label>Email</label>
        <input
          type="email"
          placeholder="Enter email"
          style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
        />

        <label>Password</label>
        <input
          type="password"
          placeholder="Enter password"
          style={{ width: "100%", marginBottom: "20px", padding: "8px" }}
        />

        <button
          onClick={handleLogin}
          style={{
            width: "100%",
            padding: "10px",
            background: "#c45f92",
            color: "pale pink",
            border: "none",
            cursor: "pointer"
          }}
        >
          SIGN IN
        </button>
      </div>
    </div>
  );
}