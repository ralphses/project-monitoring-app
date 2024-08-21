// src/components/TasksPage.js

import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import axios from 'axios';

const TasksPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { user } = location.state || {};
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    useEffect(() => {
        const userReference = user?.reference;
        const url = userReference
            ? `https://project-app-api.up.railway.app/api/v1/tasks?page=${currentPage}&user=${userReference}`
            : `https://project-app-api.up.railway.app/api/v1/tasks?page=${currentPage}`;

        axios.get(url)
            .then((response) => {
                setTasks(response.data.data.tasks);
                setTotalPages(response.data.data.noOfPages);
                setLoading(false);
            })
            .catch((error) => {
                setError(error);
                setLoading(false);
            });
    }, [user, currentPage]);

    const handlePageChange = (newPage) => {
        if (newPage > 0 && newPage <= totalPages) {
            setCurrentPage(newPage);
        }
    };

    const handleViewTask = (task) => {
        console.log(task);
        navigate(`/dashboard/tasks/${task.reference}`, { state: { task } });
    };

    if (loading) {
        return <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200">Loading...</div>;
    }

    if (error) {
        return <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200">Error: {error.message}</div>;
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200 p-4">
            <motion.h1
                className="text-3xl md:text-4xl lg:text-5xl font-bold mb-4 text-center text-gray-800"
                initial={{ opacity: 0, y: -50 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.5 }}
            >
                Tasks
            </motion.h1>
            <motion.p
                className="text-lg md:text-xl lg:text-2xl text-center text-gray-600 mb-8"
                initial={{ opacity: 0, y: 50 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.5, delay: 0.2 }}
            >
                Here are your tasks:
            </motion.p>
            <div className="w-full md:w-3/4 lg:w-1/2">
                {tasks.length === 0 ? (
                    <div className="text-center text-gray-600">No tasks available.</div>
                ) : (
                    tasks.map((task) => (
                        <motion.div
                            key={task.title}
                            className="bg-white p-6 rounded-lg shadow-lg mb-4"
                            initial={{ opacity: 0, y: 50 }}
                            animate={{ opacity: 1, y: 0 }}
                            transition={{ duration: 0.5 }}
                        >
                            <h2 className="text-xl font-bold text-gray-800 mb-2">{task.title}</h2>
                            <p className="text-gray-600 mb-2">{task.description}</p>
                            <p className="text-gray-500 text-sm mb-1">Created At: {task.createdAt}</p>
                            <p className="text-gray-500 text-sm mb-1">Expected Delivery Date: {task.expectedDeliveryDate}</p>
                            <p className="text-gray-500 text-sm mb-1">Status: {task.status}</p>
                            <button
                                className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded mt-4 transition-all duration-300 ease-in-out transform hover:scale-105"
                                onClick={() => handleViewTask(task)}
                            >
                                View Task
                            </button>
                        </motion.div>
                    ))
                )}
            </div>
            <div className="flex justify-center mt-8">
                <button
                    className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-6 rounded-l-lg transition-all duration-300 ease-in-out transform hover:scale-105"
                    onClick={() => handlePageChange(currentPage - 1)}
                    disabled={currentPage === 1}
                >
                    Previous
                </button>
                <button
                    className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-6 rounded-r-lg transition-all duration-300 ease-in-out transform hover:scale-105"
                    onClick={() => handlePageChange(currentPage + 1)}
                    disabled={currentPage === totalPages}
                >
                    Next
                </button>
            </div>
        </div>
    );
};

export default TasksPage;
