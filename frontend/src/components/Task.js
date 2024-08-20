// src/components/TaskDetailsPage.js

import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const TaskDetailsPage = () => {
    const { reference } = useParams(); // Get task reference from the URL
    const [task, setTask] = useState(null);
    const [loading, setLoading] = useState(true);
    const [newComment, setNewComment] = useState('');
    const userReference = JSON.parse(sessionStorage.getItem('user')).reference; // Get user reference from session
    const navigate = useNavigate(); // For navigating back to the progress report page

    const fetchTaskDetails = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/tasks/${reference}`);
            if (response.data.success) {
                setTask(response.data.data);
            } else {
                console.error('Failed to fetch task details');
            }
        } catch (error) {
            console.error('Error fetching task details:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTaskDetails();
    }, [reference]);

    const handleCommentSubmit = async (e) => {
        e.preventDefault();

        if (!newComment.trim()) {
            alert('Comment cannot be empty');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/v1/comments', {
                taskReference: reference,
                userReference,
                comment: newComment
            });

            if (response.data.success) {
                // Clear the comment field
                setNewComment('');
                // Re-fetch the task details to update comments
                fetchTaskDetails();
            } else {
                console.error('Failed to submit comment');
            }
        } catch (error) {
            console.error('Error submitting comment:', error);
        }
    };

    const handleBackClick = () => {
        navigate('/dashboard/progress-report'); // Navigate back to the progress report page
    };

    return (
        <div className="min-h-screen flex flex-col">
            <header className="bg-blue-600 p-4 text-white text-center">
                <h1 className="text-2xl font-bold">Task Details</h1>
            </header>
            <main className="flex flex-col items-center justify-center flex-grow p-6 bg-gradient-to-r from-green-100 to-blue-200">
                {loading ? (
                    <p className="text-center">Loading task details...</p>
                ) : task ? (
                    <div className="bg-white p-6 rounded-lg shadow-lg w-full md:w-3/4 lg:w-1/2">
                        <h2 className="text-2xl md:text-3xl font-semibold mb-4 text-gray-700">
                            {task.title}
                        </h2>
                        <p className="mb-2"><strong>Description:</strong> {task.description}</p>
                        <p className="mb-2"><strong>Status:</strong> {task.status}</p>
                        <p className="mb-2"><strong>Created At:</strong> {task.createdAt}</p>
                        <p className="mb-4"><strong>Expected Delivery:</strong> {task.expectedDeliveryDate}</p>

                        <h3 className="text-xl font-semibold mb-2 text-gray-600">Comments</h3>
                        <form onSubmit={handleCommentSubmit} className="flex flex-col mb-4">
                            <textarea
                                value={newComment}
                                onChange={(e) => setNewComment(e.target.value)}
                                className="p-2 border border-gray-300 rounded mb-2"
                                placeholder="Add a comment"
                                rows="4"
                            ></textarea>
                            <button
                                type="submit"
                                className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
                            >
                                Submit Comment
                            </button>
                        </form>

                        <ul className="mb-4">
                            {task.comments.length > 0 ? (
                                task.comments.map((comment, index) => (
                                    <li key={index} className="mb-2">
                                        <p><strong>Comment:</strong> {comment.content}</p>
                                        <p className="text-sm text-gray-500">Posted at: {comment.createdAt}</p>
                                    </li>
                                ))
                            ) : (
                                <p>No comments yet.</p>
                            )}
                        </ul>
                        <button
                            onClick={handleBackClick}
                            className="bg-gray-500 text-white py-2 px-4 rounded hover:bg-gray-600"
                        >
                            Back to Progress Report
                        </button>
                    </div>
                ) : (
                    <p className="text-center">Task not found.</p>
                )}
            </main>
            <footer className="bg-blue-600 p-4 text-white text-center">
                <p>&copy; 2024 Your Company. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default TaskDetailsPage;
