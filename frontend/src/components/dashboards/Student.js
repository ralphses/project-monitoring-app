// src/components/StudentDashboard.js

import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const StudentDashboard = ({ user }) => {
    const navigate = useNavigate();
    const [file, setFile] = useState(null);
    const [uploadSuccess, setUploadSuccess] = useState(null);
    const [uploadError, setUploadError] = useState(null);
    const [latestTask, setLatestTask] = useState({
        stageReference: "",
        task: ""
    });
    const [latestComment, setLatestComment] = useState({
        taskReference: "",
        comment: ""
    });

    useEffect(() => {
        axios.get(`https://project-app-api.up.railway.app/api/v1/project/latest-task?projectReference=${user.project.reference}`)
            .then(response => {
                setLatestTask(response.data.data);
            })
            .catch(error => {
                console.error('Error fetching latest task:', error);
            });

        axios.get(`https://project-app-api.up.railway.app/api/v1/project/latest-comment?projectReference=${user.project.reference}`)
            .then(response => {
                setLatestComment(response.data.data);
            })
            .catch(error => {
                console.error('Error fetching latest comment:', error);
            });
    }, [user.project.reference]);

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

        axios.put(`https://project-app-api.up.railway.app/api/v1/project/${user.reference}`, formData)
            .then(response => {
                setUploadSuccess('File uploaded successfully.');
                setUploadError(null);
            })
            .catch(error => {
                setUploadError('Failed to upload file.');
                setUploadSuccess(null);
            });
    };

    const downloadProjectFile = async () => {
        try {
            if (user.project && user.project.reference) {
                const response = await axios.get('https://project-app-api.up.railway.app/api/v1/project/download', {
                    params: { projectReference: user.project.reference },
                    responseType: 'blob', // Important to handle the file download
                });

                // Create a URL for the file and trigger the download
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', user.project.projectFile); // Use the projectFile name from DTO
                document.body.appendChild(link);
                link.click();
                link.remove();
            } else {
                console.error('No project file available for download.');
            }
        } catch (error) {
            console.error('Error downloading project file:', error);
        }
    };

    const handleLogout = () => {
        sessionStorage.clear();
        navigate('/');
    };

    return (
        <div className="bg-gray-100 min-h-screen flex flex-col">
            {/* Hero Section */}
            <header className="bg-indigo-600 text-white py-12">
                <div className="container mx-auto px-6 text-center">
                    <p className="text-2xl mt-4">Project Topic: {user.project.title}</p>
                    <div className="mt-8">
                        <motion.div
                            className="inline-block"
                            whileHover={{ scale: 1.1 }}
                            whileTap={{ scale: 0.95 }}
                        >
                            <Link
                                to={`/dashboard/progress-report`}
                                className="bg-gradient-to-r from-purple-500 to-pink-400 hover:from-purple-600 hover:to-pink-500 text-white font-bold py-3 px-6 rounded-lg shadow-lg block text-center transition-transform duration-300"
                            >
                                View Progress Report
                            </Link>
                        </motion.div>
                        <motion.button
                            onClick={handleLogout}
                            className="ml-4 bg-gradient-to-r from-red-500 to-pink-400 hover:from-red-600 hover:to-pink-500 text-white font-bold py-3 px-6 rounded-lg shadow-lg transition-transform duration-300 transform hover:scale-105"
                            whileHover={{ scale: 1.1 }}
                            whileTap={{ scale: 0.95 }}
                        >
                            Logout
                        </motion.button>
                    </div>
                </div>
            </header>

            {/* Features Section */}
            <section className="container mx-auto px-6 py-12">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                    {/* Latest Task Card */}
                    <motion.div
                        className="bg-white p-8 rounded-lg shadow-lg"
                        initial={{ opacity: 0, y: 20 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.6 }}
                    >
                        <h3 className="text-2xl font-semibold text-center mb-4">Latest Task</h3>
                        {latestTask ? (
                            <div className="text-center">
                                <p className="mb-4">{latestTask.task}</p>
                                <Link
                                    to={`/dashboard/progress-report`}
                                    className="bg-gradient-to-r from-blue-500 to-teal-400 hover:from-blue-600 hover:to-teal-500 text-white font-bold py-2 px-6 rounded-lg shadow-lg transition-transform duration-300"
                                >
                                    View Progress Report
                                </Link>
                            </div>
                        ) : (
                            <p className="text-center text-gray-500">No tasks available.</p>
                        )}
                    </motion.div>

                    {/* Latest Comment Card */}
                    <motion.div
                        className="bg-white p-8 rounded-lg shadow-lg"
                        initial={{ opacity: 0, y: 20 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.6, delay: 0.2 }}
                    >
                        <h3 className="text-2xl font-semibold text-center mb-4">Latest Comment</h3>
                        {latestComment ? (
                            <div className="text-center">
                                <p className="mb-4">{latestComment.comment}</p>
                                <Link
                                    to={`/dashboard/tasks/${latestComment.taskReference}`}
                                    className="bg-gradient-to-r from-green-500 to-lime-400 hover:from-green-600 hover:to-lime-500 text-white font-bold py-2 px-6 rounded-lg shadow-lg transition-transform duration-300"
                                >
                                    View Task
                                </Link>
                            </div>
                        ) : (
                            <p className="text-center text-gray-500">No comments available.</p>
                        )}
                    </motion.div>
                </div>

                {/* File Upload and Download Section */}
                <motion.div
                    className="bg-white p-8 rounded-lg shadow-lg mt-8"
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.6, delay: 0.4 }}
                >
                    <h3 className="text-3xl font-bold text-center mb-6">Upload Project File</h3>
                    <div className="text-center">
                        <label
                            htmlFor="file-upload"
                            className="cursor-pointer inline-flex items-center justify-center border-4 border-dashed border-indigo-500 hover:border-indigo-600 rounded-lg py-6 px-12 text-indigo-500 hover:text-indigo-600 transition-transform duration-300 transform hover:scale-105"
                        >
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="h-10 w-10 mr-2"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth="2"
                                    d="M12 4v16m8-8H4"
                                />
                            </svg>
                            <span className="text-lg font-semibold">
                                {file ? file.name : 'Click to select a file'}
                            </span>
                        </label>
                        <input
                            id="file-upload"
                            type="file"
                            onChange={handleFileChange}
                            className="hidden"
                        />
                        <button
                            onClick={handleFileUpload}
                            className="mt-4 bg-gradient-to-r from-indigo-500 to-purple-400 hover:from-indigo-600 hover:to-purple-500 text-white font-bold py-3 px-6 rounded-lg shadow-lg transition-transform duration-300 transform hover:scale-105"
                        >
                            Upload
                        </button>
                    </div>
                    {uploadSuccess && (
                        <p className="text-green-600 mt-4 text-lg text-center">{uploadSuccess}</p>
                    )}
                    {uploadError && (
                        <p className="text-red-600 mt-4 text-lg text-center">{uploadError}</p>
                    )}
                    <div className="text-center mt-8">
                        <motion.button
                            onClick={downloadProjectFile}
                            className={`bg-gradient-to-r from-blue-500 to-teal-400 hover:from-blue-600 hover:to-teal-500 text-white font-bold py-3 px-6 rounded-lg shadow-lg transition-transform duration-300 transform hover:scale-105`}
                            whileHover={{ scale: 1.1 }}
                            whileTap={{ scale: 0.95 }}
                        >
                            Download Latest Corrected Project File
                        </motion.button>
                    </div>
                </motion.div>
            </section>
        </div>
    );
};

export default StudentDashboard;
