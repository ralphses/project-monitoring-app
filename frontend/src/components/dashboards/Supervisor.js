// src/components/SupervisorDashboard.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

const SupervisorDashboard = () => {
    const [students, setStudents] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [notifications, setNotifications] = useState([]);
    const [showNotifications, setShowNotifications] = useState(false);

    const navigate = useNavigate();
    const user = JSON.parse(sessionStorage.getItem('user'));
    const supervisorReference = user.reference; // Replace this with actual supervisor reference from authentication/session

    useEffect(() => {
        // Fetch students assigned to this supervisor
        const fetchStudents = async (page) => {
            try {
                const response = await axios.get('https://project-app-api.up.railway.app/api/v1/users/students', {
                    params: {
                        page: page,
                        supervisorReference: supervisorReference,
                    },
                });

                setStudents(response.data.data.students);
                setTotalPages(response.data.data.totalPages);
            } catch (error) {
                console.error('Error fetching students:', error);
            }
        };

        // Fetch notifications for the supervisor
        const fetchNotifications = async () => {
            try {
                const response = await axios.get('https://project-app-api.up.railway.app/api/v1/notifications', {
                    params: {
                        supervisorReference: supervisorReference
                    },
                });
                setNotifications(response.data);
            } catch (error) {
                console.error('Error fetching notifications:', error);
            }
        };

        fetchStudents(currentPage);
        fetchNotifications();
    }, [currentPage, supervisorReference]);

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    const handleLogout = () => {
        sessionStorage.clear();
        navigate('/login');
    };

    const toggleNotifications = () => {
        setShowNotifications(!showNotifications);
    };

    return (
        <div className="container mx-auto p-4">
            {/* Header with Logout and Notifications */}
            <div className="flex justify-between items-center mb-6">
                <h2 className="text-2xl font-bold">Supervisor Dashboard</h2>
                <div className="flex items-center space-x-4">
                    {/* Notifications Button */}
                    <div className="relative">
                        <button
                            onClick={toggleNotifications}
                            className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
                        >
                            Notifications
                        </button>
                        {showNotifications && (
                            <div className="absolute right-0 mt-2 w-64 bg-white rounded-lg shadow-lg overflow-hidden z-50">
                                <ul>
                                    {notifications.length > 0 ? (
                                        notifications.map((notification, index) => (
                                            <li key={index} className="p-2 border-b last:border-none hover:bg-gray-100">
                                                {notification.message}
                                            </li>
                                        ))
                                    ) : (
                                        <li className="p-2 text-gray-500">No new notifications</li>
                                    )}
                                </ul>
                            </div>
                        )}
                    </div>
                    {/* Logout Button */}
                    <button
                        onClick={handleLogout}
                        className="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600"
                    >
                        Logout
                    </button>
                </div>
            </div>

            {/* Students List Section */}
            <div className="bg-white p-4 rounded-lg shadow-lg">
                <h3 className="text-xl font-semibold mb-4">Assigned Students</h3>
                <table className="w-full table-auto border-collapse">
                    <thead>
                    <tr>
                        <th className="border px-4 py-2">Matric Number</th>
                        <th className="border px-4 py-2">Name</th>
                        <th className="border px-4 py-2">Project Topic</th>
                        <th className="border px-4 py-2">Project Status</th>
                        <th className="border px-4 py-2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {students.length > 0 ? (
                        students.map((student, index) => (
                            <tr key={index}>
                                <td className="border px-4 py-2">{student.matric}</td>
                                <td className="border px-4 py-2">{student.name}</td>
                                <td className="border px-4 py-2">
                                    {student.project ? student.project.title : 'Project not created'}
                                </td>
                                <td className="border px-4 py-2">
                                    {student.project ? student.project.status : 'Project not created'}
                                </td>
                                <td className="border px-4 py-2">
                                    {student.project ? (
                                        <Link
                                            to={`/dashboard/student/project/${student.reference}`}
                                            className="text-blue-500 hover:underline"
                                        >
                                            View Project
                                        </Link>
                                    ) : (
                                        'No action available'
                                    )}
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="5" className="border px-4 py-2 text-center text-gray-500">
                                No students assigned to you at the moment.
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>

                {/* Pagination */}
                <div className="flex justify-center mt-4">
                    <nav>
                        <ul className="flex list-none">
                            {[...Array(totalPages).keys()].map((number) => (
                                <li key={number + 1} className="mx-1">
                                    <button
                                        onClick={() => paginate(number + 1)}
                                        className={`px-3 py-2 rounded-lg ${currentPage === number + 1 ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'}`}
                                    >
                                        {number + 1}
                                    </button>
                                </li>
                            ))}
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    );
};

export default SupervisorDashboard;
