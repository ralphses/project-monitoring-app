// src/components/StudentDashboard.js

import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import axios from 'axios';

const StudentDashboard = ({ user }) => {
    const [file, setFile] = useState(null);
    const [uploadSuccess, setUploadSuccess] = useState(null);
    const [uploadError, setUploadError] = useState(null);

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleFileUpload = () => {
        if (!file) {
            setUploadError('Please select a file to upload.');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);

        axios.post(`http://localhost:8080/api/v1/projects/${user.id}/upload`, formData)
            .then(response => {
                setUploadSuccess('File uploaded successfully.');
                setUploadError(null);
            })
            .catch(error => {
                setUploadError('Failed to upload file.');
                setUploadSuccess(null);
            });
    };

    return (
        <div
            className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200 p-4">
            <motion.h2
                className="text-2xl md:text-3xl lg:text-4xl font-semibold mb-4 text-center text-gray-700"
                initial={{opacity: 0, y: 50}}
                animate={{opacity: 1, y: 0}}
                transition={{duration: 0.5, delay: 0.2}}
            >
                Project Topic: {user.project}
            </motion.h2>
            <motion.div
                className="bg-white p-6 rounded-lg shadow-lg w-full md:w-3/4 lg:w-1/2 mb-8"
                initial={{opacity: 0, scale: 0.8}}
                animate={{opacity: 1, scale: 1}}
                transition={{duration: 0.5, delay: 0.3}}
            >
                <h3 className="text-xl font-semibold mb-2 text-center">Latest Task</h3>
                {user.latestTask ? (
                    <p className="text-center">{user.latestTask.title} ({user.latestTask.status})</p>
                ) : (
                    <p className="text-center">No tasks available.</p>
                )}
            </motion.div>
            <motion.div
                className="bg-white p-6 rounded-lg shadow-lg w-full md:w-3/4 lg:w-1/2 mb-8"
                initial={{opacity: 0, scale: 0.8}}
                animate={{opacity: 1, scale: 1}}
                transition={{duration: 0.5, delay: 0.4}}
            >
                <h3 className="text-xl font-semibold mb-2 text-center">Latest Comment from Supervisor</h3>
                {user.latestSupervisorComment ? (
                    <p className="text-center">{user.latestSupervisorComment}</p>
                ) : (
                    <p className="text-center">No comments available.</p>
                )}
            </motion.div>
            <div className="flex flex-col md:flex-row justify-center md:space-x-4 w-full md:w-3/4 lg:w-1/2">
                <motion.div
                    className="mb-4 md:mb-0 w-full md:w-auto"
                    whileHover={{scale: 1.05}}
                    whileTap={{scale: 0.95}}
                >
                    <Link
                        to={{
                            pathname: '/dashboard/tasks',
                            state: {user},
                        }}
                        className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 rounded-lg shadow-lg w-full md:w-auto block text-center"
                    >
                        View Tasks
                    </Link>
                </motion.div>
                <motion.div
                    className="mb-4 md:mb-0 w-full md:w-auto"
                    whileHover={{scale: 1.05}}
                    whileTap={{scale: 0.95}}
                >
                    <Link
                        to="/dashboard/progress-report"
                        className="bg-green-500 hover:bg-green-600 text-white font-bold py-3 px-6 rounded-lg shadow-lg w-full md:w-auto block text-center"
                    >
                        Progress Report
                    </Link>
                </motion.div>
                <motion.div
                    className="w-full md:w-auto"
                    whileHover={{scale: 1.05}}
                    whileTap={{scale: 0.95}}
                >
                    <Link
                        to="/dashboard/comments"
                        className="bg-purple-500 hover:bg-purple-600 text-white font-bold py-3 px-6 rounded-lg shadow-lg w-full md:w-auto block text-center"
                    >
                        View Comments
                    </Link>
                </motion.div>
            </div>
            <div className="flex flex-col items-center mt-8 w-full md:w-3/4 lg:w-1/2 bg-white p-6 rounded-lg shadow-lg">
                <motion.h3
                    className="text-2xl font-bold mb-4 text-center text-purple-600"
                    initial={{opacity: 0, scale: 0.8}}
                    animate={{opacity: 1, scale: 1}}
                    transition={{duration: 0.5, delay: 0.5}}
                >
                    Upload Project File (New or Updated)
                </motion.h3>
                <motion.div
                    className="w-full flex flex-col items-center"
                    initial={{opacity: 0, scale: 0.8}}
                    animate={{opacity: 1, scale: 1}}
                    transition={{duration: 0.5, delay: 0.6}}
                >
                    <div className="w-full mb-4">
                        <label
                            htmlFor="file-upload"
                            className="cursor-pointer flex flex-col items-center justify-center border-4 border-dashed border-blue-500 hover:border-blue-600 rounded-lg p-6 transition-all duration-300 ease-in-out transform hover:scale-105 text-blue-500 hover:text-blue-600"
                        >
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="h-10 w-10 mb-2"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth="2"
                                    d="M7 16V12M7 12V8M7 8L5 10m4-2V8l2-2m0 0l4 4m-4-4v4m0 4v8m-4 2h8m-8 0a4 4 0 004 4m0-4a4 4 0 004 4"
                                />
                            </svg>
                            <span className="font-medium text-center">
                    Drag & Drop or <span className="text-blue-600 underline">Browse</span> to Upload
                </span>
                            <input
                                id="file-upload"
                                type="file"
                                className="hidden"
                                onChange={handleFileChange}
                            />
                        </label>
                    </div>
                    <button
                        className="bg-gradient-to-r from-purple-500 to-blue-500 hover:from-purple-600 hover:to-blue-600 text-white font-bold py-2 px-6 rounded-lg transition-all duration-300 ease-in-out transform hover:scale-105 shadow-lg"
                        onClick={handleFileUpload}
                    >
                        Upload File
                    </button>
                </motion.div>
                {uploadError && <p className="text-red-500 mt-4">{uploadError}</p>}
                {uploadSuccess && <p className="text-green-500 mt-4">{uploadSuccess}</p>}
            </div>

        </div>
    );
};

export default StudentDashboard;
