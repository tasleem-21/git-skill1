import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Home from "./pages/Home";
import Dashboard from "./pages/Dashboard";
import Student from "./pages/Student";
import Faculty from "./pages/faculty";
import Courses from "./pages/Courses";
import Contact from "./pages/Contact";
import Reports from "./pages/Reports";

import Layout from "./Components/Layout";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* NO NAVBAR */}
        <Route path="/" element={<Login />} />

        {/* WITH NAVBAR */}
        <Route path="/home" element={<Layout><Home /></Layout>} />
        <Route path="/dashboard" element={<Layout><Dashboard /></Layout>} />
        <Route path="/student" element={<Layout><Student /></Layout>} />
        <Route path="/faculty" element={<Layout><Faculty /></Layout>} />
        <Route path="/courses" element={<Layout><Courses /></Layout>} />
        <Route path="/contact" element={<Layout><Contact /></Layout>} />
        <Route path="/reports" element={<Layout><Reports /></Layout>} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;