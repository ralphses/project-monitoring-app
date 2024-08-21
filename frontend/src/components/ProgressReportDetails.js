import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const ProgressReportDetails = () => {
    const { projectReference } = useParams(); // Getting the project's reference from the URL
    const [progressReport, setProgressReport] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch the progress report details
        const fetchProgressReport = async () => {
            try {
                const response = await axios.get(`https://project-app-api.up.railway.app/api/v1/progress-report/${projectReference}`);
                setProgressReport(response.data.data);
            } catch (error) {
                console.error('Error fetching progress report:', error);
            }
        };

        fetchProgressReport();
    }, [projectReference]);

    const handleViewTasks = (stageReference) => {
        navigate(`/dashboard/student/progress-report/stage/tasks/${stageReference}`);
    };

    const handleCreateTask = () => {
        navigate(`/dashboard/students/create-progress-report/${projectReference}`);
    };

    const handleMarkAsCompleted = async (stageReference) => {
        try {
            const response = await axios.put('https://project-app-api.up.railway.app/api/v1/progress-report/stages', {
                reference: stageReference,
                newStatus: 'COMPLETED',
            });

            if (response.data.success) {
                // Update the local state to reflect the status change and increment the level
                setProgressReport(prevReport => ({
                    ...prevReport,
                    stages: prevReport.stages.map(stage =>
                        stage.reference === stageReference ?
                            { ...stage, completed: true, level: stage.level + 1 } : stage
                    ),
                }));
            } else {
                console.error('Failed to mark stage as completed');
            }
        } catch (error) {
            console.error('Error marking stage as completed:', error);
        }
    };

    const goBack = () => {
        navigate(-1); // Navigates to the previous page
    };

    if (!progressReport) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container mx-auto p-4">
            <button
                onClick={goBack}
                className="bg-gray-500 text-white px-4 py-2 rounded-lg mb-4 hover:bg-gray-600"
            >
                Go Back
            </button>

            <h2 className="text-2xl font-bold mb-4">Progress Report</h2>

            {progressReport.stages.length > 0 ? (
                progressReport.stages.map(stage => (
                    <div key={stage.reference} className="bg-white p-4 rounded-lg shadow-lg mb-6">
                        <h3 className="text-xl font-semibold mb-4">{stage.name}</h3>
                        <p><strong>Level:</strong> {stage.level}</p>
                        <p><strong>Completed:</strong> {stage.completed ? 'Yes' : 'No'}</p>
                        <p><strong>Expected Completion Date:</strong> {stage.completionDate || 'N/A'}</p>

                        <div className="flex space-x-4 mt-4">
                            {stage.tasks.length > 0 && (
                                <button
                                    onClick={() => handleViewTasks(stage.reference)}
                                    className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
                                >
                                    View Tasks
                                </button>
                            )}

                            <button
                                onClick={() => handleMarkAsCompleted(stage.reference)}
                                className={`px-4 py-2 rounded-lg ${stage.completed ? 'bg-gray-300 text-gray-600 cursor-not-allowed' : 'bg-green-500 text-white hover:bg-green-600'}`}
                                disabled={stage.completed}
                            >
                                {stage.completed ? 'Completed' : 'Mark as Completed'}
                            </button>
                        </div>
                    </div>
                ))
            ) : (
                <p>No stages available.</p>
            )}

            <button
                onClick={handleCreateTask}
                className="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 mt-4"
            >
                Create Progress Stage
            </button>
        </div>
    );
};

export default ProgressReportDetails;
