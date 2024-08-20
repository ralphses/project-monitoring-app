// src/components/TaskList.js

import React, {useEffect, useState} from 'react';
import {useParams, Link} from 'react-router-dom';
import axios from 'axios';

const TaskList = () => {
    const {stageReference} = useParams(); // Get stageReference from URL
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                // Updated the API request to match the new endpoint
                const response = await axios.get(`http://localhost:8080/api/v1/tasks?stageReference=${stageReference}`);

                if (response.data.success) {
                    // Updated to match the response structure
                    setTasks(response.data.data);

                    console.log(response.data.data);
                } else {
                    console.error('Failed to fetch tasks');
                }
            } catch (error) {
                console.error('Error fetching tasks:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchTasks();
    }, [stageReference]);

    return (
        <div
            className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200 p-4">
            <h2 className="text-2xl md:text-3xl lg:text-4xl font-semibold mb-4 text-center text-gray-700">
                Task List
            </h2>
            <div className="bg-white p-6 rounded-lg shadow-lg w-full md:w-3/4 lg:w-1/2">
                {loading ? (
                    <p className="text-center">Loading tasks...</p>
                ) : tasks.length > 0 ? (
                    <ul className="list-disc list-inside">
                        {tasks.map((task, index) => (
                            <li key={index} className="mb-4">
                                {/* Link to the task details page */}
                                <Link
                                    to={`/dashboard/tasks/${task.reference}`}
                                    className="text-blue-500 hover:underline text-lg font-bold"
                                >
                                    {task.title}
                                </Link>
                                <p>{task.description}</p>
                                <p>Status: {task.status}</p>
                                <p>Created At: {task.createdAt}</p>
                                <p>Expected Delivery: {task.expectedDeliveryDate}</p>

                                <p className="text-green-500 hover:underline">Comments ({task.comments.length}) </p>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p className="text-center">No tasks available for this stage.</p>
                )}
            </div>
        </div>
    );
};

export default TaskList;
