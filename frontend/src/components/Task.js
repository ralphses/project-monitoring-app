import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import axios from 'axios';

const TaskDetailsPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { task } = location.state || {};
    const [comment, setComment] = useState('');
    const [status, setStatus] = useState(task.status);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [comments, setComments] = useState([]);

    useEffect(() => {
        // Fetch task details including comments when component mounts
        if (task.reference) {
            axios.get(`http://localhost:8080/api/v1/tasks/${task.reference}`)
                .then(response => {
                    setStatus(response.data.data.status);
                    setComments(response.data.data.comments);
                })
                .catch(error => {
                    console.error('Failed to fetch task details:', error);
                });
        }
    }, [task.reference]);

    const handleAddComment = () => {
        axios.post(`http://localhost:8080/api/v1/tasks/${task.reference}/comments?comment`, { text: comment })
            .then(response => {
                setSuccess('Comment added successfully.');
                setComments([...comments, response.data.data]);
                setComment(''); // Clear comment input after adding
                setError(null);
            })
            .catch(error => {
                setError('Failed to add comment.');
                setSuccess(null);
            });
    };

    const handleChangeStatus = () => {
        axios.put(`http://localhost:8080/api/v1/tasks/${task.reference}/status?status=${status}`)
            .then(response => {
                setStatus(response.data.data);
                setSuccess('Status updated successfully.');
                setError(null);
            })
            .catch(error => {
                setError('Failed to update status.');
                setSuccess(null);
            });
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200 p-4">
            <motion.h1
                className="text-3xl md:text-4xl lg:text-5xl font-bold mb-4 text-center text-gray-800"
                initial={{ opacity: 0, y: -50 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.5 }}
            >
                Task Details
            </motion.h1>
            <div className="bg-white p-6 rounded-lg shadow-lg mb-4 w-full md:w-3/4 lg:w-1/2">
                <h2 className="text-xl font-bold text-gray-800 mb-2">{task.title}</h2>
                <p className="text-gray-600 mb-2">{task.description}</p>
                <p className="text-gray-500 text-sm mb-1">Created At: {task.createdAt}</p>
                <p className="text-gray-500 text-sm mb-1">Expected Delivery Date: {task.expectedDeliveryDate}</p>
                <p className="text-gray-500 text-sm mb-1">Status: {status}</p>
                <div className="mt-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2">Add Comment:</label>
                    <textarea
                        className="w-full p-2 border border-gray-300 rounded-lg mb-4"
                        rows="4"
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                    ></textarea>
                    <button
                        className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded transition-all duration-300 ease-in-out transform hover:scale-105 mb-4"
                        onClick={handleAddComment}
                    >
                        Add Comment
                    </button>
                </div>
                <div className="mt-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2">Change Status:</label>
                    <select
                        className="w-full p-2 border border-gray-300 rounded-lg mb-4"
                        value={status}
                        onChange={(e) => setStatus(e.target.value)}
                    >
                        <option value="INITIATED">Initiated</option>
                        <option value="DECLINED">Declined</option>
                        <option value="IN_PROGRESS">In Progress</option>
                        <option value="COMPLETED">Completed</option>
                        <option value="FAILED">Failed</option>
                    </select>
                    <button
                        className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded transition-all duration-300 ease-in-out transform hover:scale-105"
                        onClick={handleChangeStatus}
                    >
                        Change Status
                    </button>
                </div>
                {error && <p className="text-red-500 mt-4">{error}</p>}
                {success && <p className="text-green-500 mt-4">{success}</p>}
                <div className="mt-6">
                    <h3 className="text-lg font-bold mb-2">Comments:</h3>
                    {comments.length === 0 ? (
                        <p className="text-gray-600">No comments available.</p>
                    ) : (
                        <ul className="list-disc pl-4">
                            {comments.map((comment, index) => (
                                <li key={index} className="text-gray-600 mb-2">
                                    <span className="font-semibold">{comment.createdAt}: </span>
                                    {comment.content}
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
            <button
                className="bg-gray-500 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded transition-all duration-300 ease-in-out transform hover:scale-105"
                onClick={() => navigate(-1)}
            >
                Back to Tasks
            </button>
        </div>
    );
};

export default TaskDetailsPage;
