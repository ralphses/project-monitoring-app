// src/components/CreateProgressReportStage.js

import React, { useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const CreateProgressReportStage = () => {
    const { projectReference } = useParams(); // Getting the project reference from the URL
    const [stages, setStages] = useState([
        {
            stage: '',
            stageExpectedCompletionDate: '',
            tasks: [{ title: '', description: '', expectedCompletionDate: '' }]
        }
    ]); // Initial state for stages
    const navigate = useNavigate();

    const handleStageChange = (index, event) => {
        const { name, value } = event.target;
        const newStages = [...stages];
        newStages[index] = { ...newStages[index], [name]: value };
        setStages(newStages);
    };

    const handleTaskChange = (stageIndex, taskIndex, event) => {
        const { name, value } = event.target;
        const newStages = [...stages];
        newStages[stageIndex].tasks[taskIndex] = {
            ...newStages[stageIndex].tasks[taskIndex],
            [name]: value
        };
        setStages(newStages);
    };

    const addTask = (index) => {
        const newStages = [...stages];
        newStages[index].tasks.push({ title: '', description: '', expectedCompletionDate: '' });
        setStages(newStages);
    };

    const removeTask = (stageIndex, taskIndex) => {
        const newStages = [...stages];
        newStages[stageIndex].tasks.splice(taskIndex, 1);
        setStages(newStages);
    };

    const addStage = () => {
        setStages([...stages, {
            stage: '',
            stageExpectedCompletionDate: '',
            tasks: [{ title: '', description: '', expectedCompletionDate: '' }]
        }]);
    };

    const removeStage = (index) => {
        if (stages.length > 1) { // Ensure at least one stage remains
            const newStages = [...stages];
            newStages.splice(index, 1);
            setStages(newStages);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        console.log(stages);
        try {
            await axios.post('http://localhost:8080/api/v1/progress-report/stages', {
                projectReference,
                stages: stages.map(stage => ({
                    stage: stage.stage,
                    stageExpectedCompletionDate: stage.stageExpectedCompletionDate,
                    tasks: stage.tasks.map(task => ({
                        title: task.title,
                        description: task.description,
                        expectedCompletionDate: task.expectedCompletionDate
                    }))
                }))
            });
            navigate(`/dashboard/students/progress-report/${projectReference}`);
        } catch (error) {
            console.error('Error creating progress report stages:', error);
        }
    };

    const goBack = () => {
        navigate(-1); // Navigates to the previous page
    };

    return (
        <div className="container mx-auto p-4">
            <button
                onClick={goBack}
                className="bg-gray-500 text-white px-4 py-2 rounded-lg mb-4 hover:bg-gray-600"
            >
                Go Back
            </button>

            <h2 className="text-2xl font-bold mb-4">Create Progress Report Stages</h2>

            <p className="mb-6">
                Each stage represents a milestone or phase in the student's project. For example, stages can include "Requirement Gathering," "Writing Chapter One," etc.
                Each stage can have multiple tasks or none. Please provide all necessary stages and tasks required to complete the project.
            </p>

            <form onSubmit={handleSubmit}>
                {stages.map((stage, stageIndex) => (
                    <div key={stageIndex} className="bg-white p-4 rounded-lg shadow-lg mb-6">
                        <h3 className="text-xl font-semibold mb-4">Stage {stageIndex + 1}</h3>

                        <div className="mb-4">
                            <label className="block mb-2"><strong>Stage Name:</strong></label>
                            <input
                                type="text"
                                name="stage"
                                value={stage.stage}
                                onChange={(event) => handleStageChange(stageIndex, event)}
                                className="border p-2 rounded-lg w-full"
                                required
                            />
                        </div>

                        <div className="mb-4">
                            <label className="block mb-2"><strong>Stage Expected Completion Date:</strong></label>
                            <input
                                type="date"
                                name="stageExpectedCompletionDate"
                                value={stage.stageExpectedCompletionDate}
                                onChange={(event) => handleStageChange(stageIndex, event)}
                                className="border p-2 rounded-lg w-full"
                                required
                            />
                        </div>

                        {stage.tasks.map((task, taskIndex) => (
                            <div key={taskIndex} className="mb-4">
                                <h4 className="text-lg font-semibold mb-2">Task {taskIndex + 1}</h4>
                                <label className="block mb-2"><strong>Task Title:</strong></label>
                                <input
                                    type="text"
                                    name="title"
                                    value={task.title}
                                    onChange={(event) => handleTaskChange(stageIndex, taskIndex, event)}
                                    className="border p-2 rounded-lg w-full"
                                    required
                                />

                                <label className="block mt-2 mb-2"><strong>Description:</strong></label>
                                <textarea
                                    name="description"
                                    value={task.description}
                                    onChange={(event) => handleTaskChange(stageIndex, taskIndex, event)}
                                    className="border p-2 rounded-lg w-full"
                                    required
                                ></textarea>

                                <label className="block mt-2 mb-2"><strong>Expected Completion Date:</strong></label>
                                <input
                                    type="date"
                                    name="expectedCompletionDate"
                                    value={task.expectedCompletionDate}
                                    onChange={(event) => handleTaskChange(stageIndex, taskIndex, event)}
                                    className="border p-2 rounded-lg w-full"
                                    required
                                />

                                {stage.tasks.length > 1 && (
                                    <button
                                        type="button"
                                        onClick={() => removeTask(stageIndex, taskIndex)}
                                        className="text-red-500 hover:underline mt-1"
                                    >
                                        Remove Task
                                    </button>
                                )}
                            </div>
                        ))}

                        <button
                            type="button"
                            onClick={() => addTask(stageIndex)}
                            className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
                        >
                            Add Task
                        </button>

                        {stages.length > 1 && (
                            <button
                                type="button"
                                onClick={() => removeStage(stageIndex)}
                                className="text-red-500 hover:underline mt-4"
                            >
                                Remove Stage
                            </button>
                        )}
                    </div>
                ))}

                <button
                    type="button"
                    onClick={addStage}
                    className="bg-gray-500 text-white px-4 py-2 rounded-lg hover:bg-gray-600 mb-4"
                >
                    Add New Stage
                </button>

                <button
                    type="submit"
                    className="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 ml-2"
                >
                    Create Stages
                </button>
            </form>
        </div>
    );
};

export default CreateProgressReportStage;
