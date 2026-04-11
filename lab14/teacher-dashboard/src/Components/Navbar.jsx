import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <div style={{
      background: "#1976d2",
      padding: "10px",
      display: "flex",
      gap: "20px"
    }}>
      <Link to="/home" style={{ color: "white" }}>HOME</Link>
      <Link to="/student" style={{ color: "white" }}>STUDENT</Link>
      <Link to="/faculty" style={{ color: "white" }}>FACULTY</Link>
      <Link to="/courses" style={{ color: "white" }}>COURSES</Link>
      <Link to="/contact" style={{ color: "white" }}>CONTACT US</Link>
      <Link to="/reports" style={{ color: "white" }}>REPORTS</Link>
      <Link to="/" style={{ color: "white" }}>LOGIN</Link>
    </div>
  );
}