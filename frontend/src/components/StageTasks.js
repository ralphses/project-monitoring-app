// src/components/StageTasks.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate, Link } from 'react-router-dom';

const StageTasks = () => {
    const { projectReference, stageReference } = useParams(); // Get project and stage references from URL
    const [tasks, setTasks] = useState([]);
    const [stageName, setStageName] = useState(''); // Assuming the stage name is provided by your backend
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch the list of tasks for the specified stage
        const fetchTasks = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/v1/tasks?stageReference=${stageReference}`);

                // Extract data from the custom response
                const tasksData = response.data.data;

                setTasks(tasksData);

                // Assuming stage name is part of the tasks data
                if (tasksData.length > 0) {
                    setStageName(tasksData[0].stageName); // Update based on the actual data structure
                }
            } catch (error) {
                console.error('Error fetching tasks:', error);
            }
        };

        fetchTasks();
    }, [stageReference]);

    const goBack = () => {
        navigate(-1); // Navigate back to the previous page
    };

    return (
        <div className="container mx-auto p-4">
            <button
                onClick={goBack}
                className="bg-gray-500 text-white px-4 py-2 rounded-lg mb-4 hover:bg-gray-600"
            >
                Go Back
            </button>

            <h2 className="text-2xl font-bold mb-4">Tasks for {stageName}</h2>

            {tasks.length > 0 ? (
                <ul>
                    {tasks.map(task => (
                        <li key={task.reference} className="mb-4">
                            <div className="bg-white p-4 rounded-lg shadow-lg">
                                <h3 className="text-lg font-semibold">{task.title}</h3>
                                <p><strong>Description:</strong> {task.description}</p>
                                <p><strong>Status:</strong> {task.status}</p>
                                <p><strong>Expected Completion Date:</strong> {task.expectedDeliveryDate}</p>
                                <Link
                                    to={`/dashboard/student/progress-report/stage/task/${task.reference}`}
                                    className="text-blue-500 hover:underline"
                                >
                                    View Task Details
                                </Link>
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No tasks available for this stage.</p>
            )}
        </div>
    );
};

export default StageTasks;
