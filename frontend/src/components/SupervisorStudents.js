// src/components/SupervisorStudents.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const SupervisorStudents = () => {
    const { reference } = useParams(); // Get supervisor reference from URL parameters
    const [students, setStudents] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch paginated list of students for the supervisor
        const fetchStudents = async (page) => {
            try {
                const response = await axios.get('https://project-app-api.up.railway.app/api/v1/users/students', {
                    params: {
                        page: page,
                        supervisorReference: reference,
                    },
                });

                setStudents(response.data.data.students);
                setTotalPages(response.data.data.totalPages);
            } catch (error) {
                console.error('Error fetching students:', error);
            }
        };

        fetchStudents(currentPage);
    }, [currentPage, reference]);

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div className="container mx-auto p-4">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-2xl font-bold">Supervisor's Students</h2>
                <button
                    onClick={() => navigate(-1)}
                    className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
                >
                    Go Back
                </button>
            </div>

            <div className="bg-white p-4 rounded-lg shadow-lg">
                <h3 className="text-xl font-semibold mb-4">Students List</h3>
                <table className="w-full table-auto border-collapse">
                    <thead>
                    <tr>
                        <th className="border px-4 py-2">Name</th>
                        <th className="border px-4 py-2">Matric</th>
                        <th className="border px-4 py-2">Project Title</th>
                    </tr>
                    </thead>
                    <tbody>
                    {students.length > 0 ? (
                        students.map((student, index) => (
                            <tr key={index}>
                                <td className="border px-4 py-2">{student.name}</td>
                                <td className="border px-4 py-2">{student.matric}</td>
                                <td className="border px-4 py-2">{student.project ? student.project.title : 'N/A'}</td>

                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="4" className="border px-4 py-2 text-center text-gray-500">
                                No students available.
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
                                            currentPage === number + 1
                                                ? 'bg-blue-500 text-white'
                                                : 'bg-gray-200 text-gray-700'
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

export default SupervisorStudents;
