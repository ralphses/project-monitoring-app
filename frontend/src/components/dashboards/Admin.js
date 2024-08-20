// src/components/AdminDashboard.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

const AdminDashboard = () => {
    const [supervisors, setSupervisors] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch paginated list of supervisors
        const fetchSupervisors = async (page) => {
            try {
                const response = await axios.get(`http://localhost:8080/api/v1/users/supervisors`, {
                    params: {
                        page: page,
                    },
                });

                setSupervisors(response.data.data.supervisors);
                setTotalPages(response.data.data.totalPages);
            } catch (error) {
                console.error('Error fetching supervisors:', error);
            }
        };

        fetchSupervisors(currentPage);
    }, [currentPage]);

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    const handleLogout = () => {
        // Perform logout logic here, such as clearing session storage or tokens
        sessionStorage.clear(); // Example: remove token from local storage
        navigate('/login'); // Redirect to login page
    };

    return (
        <div className="container mx-auto p-4">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-2xl font-bold">Admin Dashboard</h2>
                <button
                    onClick={handleLogout}
                    className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
                >
                    Logout
                </button>
            </div>
            <p>Welcome to the admin dashboard! Here you can manage users, oversee projects, and generate reports.</p>

            {/* Add Supervisor Button */}
            <div className="mt-6">
                <Link to="/dashboard/admin/add-supervisor">
                    <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                        Add New Supervisor
                    </button>
                </Link>
            </div>

            {/* Supervisors List Section */}
            <div className="bg-white p-4 rounded-lg shadow-lg mt-6">
                <h3 className="text-xl font-semibold mb-4">Supervisors</h3>
                <table className="w-full table-auto border-collapse">
                    <thead>
                    <tr>
                        <th className="border px-4 py-2">Supervisor Name</th>
                        <th className="border px-4 py-2">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {supervisors.length > 0 ? (
                        supervisors.map((supervisor, index) => (
                            <tr key={index}>
                                <td className="border px-4 py-2">{supervisor.name}</td>
                                <td className="border px-4 py-2">
                                    <Link
                                        to={`/dashboard/supervisor/students/${supervisor.reference}`}
                                        className="text-blue-500 hover:underline"
                                    >
                                        View Students
                                    </Link>
                                    <button
                                        onClick={() => navigate(`/dashboard/supervisor/assign-student/${supervisor.reference}`)}
                                        className="ml-4 text-green-500 hover:underline"
                                    >
                                        Assign Student
                                    </button>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="2" className="border px-4 py-2 text-center text-gray-500">
                                No supervisors available.
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
                                        className={`px-3 py-2 rounded-lg ${
                                            currentPage === number + 1 ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'
                                        }`}
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

export default AdminDashboard;
