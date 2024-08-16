import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./components/Home";
import LoginPage from "./components/Login";
import RegisterPage from "./components/Register";
import Dashboard from "./components/dashboards/Dashboard";
import TasksPage from "./components/TasksPage";
import TaskDetailsPage from "./components/Task";
import ProgressReport from "./components/ProgressReport";


function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
         <Route Component={Home} path="/" ></Route>
         <Route Component={LoginPage} path="/login" ></Route>
         <Route Component={RegisterPage} path="/register" ></Route>
         <Route Component={Dashboard} path="/dashboard" ></Route>
         <Route Component={TasksPage} path="/dashboard/tasks" ></Route>
         <Route Component={TaskDetailsPage} path="/dashboard/tasks/:reference" ></Route>
         <Route Component={ProgressReport} path="/dashboard/progress-report" ></Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;