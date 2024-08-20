// src/components/TaskDetails.js

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const TaskDetails = () => {
    const { taskReference } = useParams(); // Fetch the task reference from the URL
    const [task, setTask] = useState(null); // State to hold the task details
    const [loading, setLoading] = useState(true); // State for loading status
    const [error, setError] = useState(null); // State for handling errors
    const [comment, setComment] = useState(''); // State for new comment input
    const [addingComment, setAddingComment] = useState(false); // State for comment submission status
    const [markingAsCompleted, setMarkingAsCompleted] = useState(false); // State for marking task as completed
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch the task details from the API
        const fetchTaskDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/v1/tasks/${taskReference}`);
                setTask(response.data.data); // Assuming the data is in `data` field of response
                setLoading(false);
            } catch (error) {
                setError('Error fetching task details');
                setLoading(false);
            }
        };

        fetchTaskDetails();
    }, [taskReference]);

    const goBack = () => {
        navigate(-1); // Navigate back to the previous page
    };

    const handleCommentSubmit = async (e) => {
        e.preventDefault();
        setAddingComment(true);
        const userReference = JSON.parse(sessionStorage.getItem('user')).reference; // Replace with actual user reference logic
        console.log(userReference);
        try {
            await axios.post('http://localhost:8080/api/v1/comments', {
                userReference,
                taskReference,
                comment,
            });
            // After successful submission, fetch updated task details
            const response = await axios.get(`http://localhost:8080/api/v1/tasks/${taskReference}`);
            setTask(response.data.data);
            setComment(''); // Clear the comment input
        } catch (error) {
            console.error('Error adding comment:', error);
        } finally {
            setAddingComment(false);
        }
    };

    const handleMarkAsCompleted = async () => {
        setMarkingAsCompleted(true);
        try {
            const response = await axios.put(`http://localhost:8080/api/v1/tasks?task=${taskReference}&status=COMPLETED`);
            if (response.data.success) {
                // Update the task status locally after a successful response
                setTask(prevTask => ({ ...prevTask, status: 'COMPLETED' }));
            } else {
                console.error('Failed to mark task as completed');
            }
        } catch (error) {
            console.error('Error marking task as completed:', error);
        } finally {
            setMarkingAsCompleted(false);
        }
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="container mx-auto p-4">
            <button
                onClick={goBack}
                className="bg-gray-500 text-white px-4 py-2 rounded-lg mb-4 hover:bg-gray-600"
            >
                Go Back
            </button>

            <h2 className="text-2xl font-bold mb-4">{task.title}</h2>

            <div className="bg-white p-4 rounded-lg shadow-lg mb-6">
                <p><strong>Description:</strong> {task.description}</p>
                <p><strong>Status:</strong> {task.status}</p>
                <p><strong>Created At:</strong> {task.createdAt}</p>
                <p><strong>Expected Delivery Date:</strong> {task.expectedDeliveryDate}</p>
            </div>

            {task.comments && task.comments.length > 0 && (
                <div className="bg-white p-4 rounded-lg shadow-lg mb-6">
                    <h3 className="text-xl font-semibold mb-4">Comments</h3>
                    <ul>
                        {task.comments.map((comment, index) => (
                            <li key={index} className="mb-2">
                                <p><strong>Comment:</strong> {comment.content}</p>
                                <p><strong>Date:</strong> {comment.createdAt}</p>
                            </li>
                        ))}
                    </ul>
                </div>
            )}

            {/* Add Comment Section */}
            <div className="bg-white p-4 rounded-lg shadow-lg mb-6">
                <h3 className="text-xl font-semibold mb-4">Add a Comment</h3>
                <form onSubmit={handleCommentSubmit}>
                    <textarea
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        className="border p-2 rounded-lg w-full mb-4"
                        placeholder="Enter your comment..."
                        required
                    ></textarea>
                    <button
                        type="submit"
                        className="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600"
                        disabled={addingComment}
                    >
                        {addingComment ? 'Adding Comment...' : 'Add Comment'}
                    </button>
                </form>
            </div>

            {/* Mark as Completed Button */}
            <button
                onClick={handleMarkAsCompleted}
                className={`bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 ${task.status === 'COMPLETED' ? 'bg-gray-300 text-gray-600 cursor-not-allowed' : ''}`}
                disabled={task.status === 'COMPLETED' || markingAsCompleted}
            >
                {markingAsCompleted ? 'Marking as Completed...' : task.status === 'COMPLETED' ? 'Task Completed' : 'Mark as Completed'}
            </button>
        </div>
    );
};

export default TaskDetails;
