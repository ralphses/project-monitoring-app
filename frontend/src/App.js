import {BrowserRouter, Route, Routes} from "react-router-dom";
import "./App.css";
import Home from "./components/Home";
import LoginPage from "./components/Login";
import RegisterPage from "./components/Register";
import Dashboard from "./components/dashboards/Dashboard";
import TasksPage from "./components/TasksPage";
import TaskDetailsPage from "./components/Task";
import ProgressReport from "./components/ProgressReport";
import CreateProject from "./components/CreateProject";
import TaskList from "./components/TaskList";
import StudentProjectDetails from "./components/StudentProjectDetails";
import ProgressReportDetails from "./components/ProgressReportDetails";
import CreateProgressReportStage from "./components/CreateProgressReportStage";
import StageTasks from "./components/StageTasks";
import TaskDetails from "./components/TaskDetails";
import AddSupervisor from "./components/AddSupervisor";
import SupervisorStudents from "./components/SupervisorStudents";
import AssignStudentPage from "./components/AssignStudentPage";


function App() {

    return (
        <>
            <BrowserRouter>
                <Routes>
                    <Route Component={Home} path="/"></Route>
                    <Route Component={LoginPage} path="/login"></Route>
                    <Route Component={RegisterPage} path="/register"></Route>
                    <Route Component={Dashboard} path="/dashboard"></Route>
                    <Route Component={TasksPage} path="/dashboard/tasks"></Route>
                    <Route Component={TaskDetailsPage} path="/dashboard/tasks/:reference"></Route>
                    <Route Component={ProgressReport} path="/dashboard/progress-report"></Route>
                    <Route Component={CreateProject} path="/dashboard/create-project"></Route>
                    <Route Component={TaskList} path="/dashboard/progress-report/tasks/:stageReference"></Route>
                    <Route Component={StudentProjectDetails} path="/dashboard/student/project/:studentReference"></Route>
                    <Route Component={ProgressReportDetails} path="/dashboard/students/progress-report/:projectReference"></Route>
                    <Route Component={CreateProgressReportStage} path="/dashboard/students/create-progress-report/:projectReference"></Route>
                    <Route Component={StageTasks} path="/dashboard/student/progress-report/stage/tasks/:stageReference"></Route>
                    <Route Component={TaskDetails} path="/dashboard/student/progress-report/stage/task/:taskReference"></Route>
                    <Route Component={AddSupervisor} path="/dashboard/admin/add-supervisor"></Route>
                    <Route Component={SupervisorStudents} path="/dashboard/supervisor/students/:reference"></Route>
                    <Route Component={AssignStudentPage} path="/dashboard/supervisor/assign-student/:reference"></Route>
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;