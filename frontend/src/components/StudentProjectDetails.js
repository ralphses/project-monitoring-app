// src/components/StudentProjectDetails.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const StudentProjectDetails = () => {
    const { studentReference } = useParams(); // Getting the student's reference from the URL
    const [project, setProject] = useState(null);
    const [latestComment, setLatestComment] = useState('');
    const [latestTask, setLatestTask] = useState('');
    const [taskStatus, setTaskStatus] = useState('');
    const [file, setFile] = useState(null); // State for handling the file upload
    const [uploadError, setUploadError] = useState(null);
    const [uploadSuccess, setUploadSuccess] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch the project, latest task, and latest comment details
        const fetchProjectDetails = async () => {
            try {
                const projectResponse = await axios.get('https://project-app-api.up.railway.app/api/v1/project/student', {
                    params: { reference: studentReference },
                });
                setProject(projectResponse.data.data);

                const latestTaskResponse = await axios.get('https://project-app-api.up.railway.app/api/v1/project/latest-task', {
                    params: { projectReference: projectResponse.data.data.reference },
                });
                setLatestTask(latestTaskResponse.data.data.task);
                setTaskStatus(latestTaskResponse.data.data.status);

                const latestCommentResponse = await axios.get('https://project-app-api.up.railway.app/api/v1/project/latest-comment', {
                    params: { projectReference: projectResponse.data.data.reference },
                });
                setLatestComment(latestCommentResponse.data.data.comment);
            } catch (error) {
                console.error('Error fetching project details:', error);
            }
        };

        fetchProjectDetails();
    }, [studentReference]);

    const handleApproveOrDisapprove = async () => {
        const newStatus = project.status === 'INITIATED' || project.status === 'IN_PROGRESS' ? 'DECLINED' : 'IN_PROGRESS';
        try {
            await axios.put('https://project-app-api.up.railway.app/api/v1/project/update-status', null, {
                params: {
                    projectReference: project.reference,
                    newStatus: newStatus,
                },
            });
            setProject((prevProject) => ({
                ...prevProject,
                status: newStatus,
            }));
        } catch (error) {
            console.error('Error updating project status:', error);
        }
    };

    const viewProgressReport = () => {
        navigate(`/dashboard/students/progress-report/${project.reference}`);
    };

    const createProgressReport = () => {
        navigate(`/dashboard/students/create-progress-report/${project.reference}`);
    };

    const goBack = () => {
        navigate(-1); // Navigates to the previous page
    };

    const handleFileUpload = async () => {
        if (!file) {
            setUploadError('Please select a file to upload.');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);

        try {
            await axios.put(`https://project-app-api.up.railway.app/api/v1/project/${studentReference}`, formData);
            setUploadSuccess('File uploaded successfully.');
            setUploadError(null);
        } catch (error) {
            setUploadError('Failed to upload file.');
            setUploadSuccess(null);
            console.error('Error uploading project file:', error);
        }
    };

    const downloadProjectFile = async () => {
        try {
            if (project.projectFile) {
                const response = await axios.get('https://project-app-api.up.railway.app/api/v1/project/download', {
                    params: { projectReference: project.reference },
                    responseType: 'blob', // Important to handle the file download
                });

                // Create a URL for the file and trigger the download
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', project.projectFile); // Use the projectFile name from DTO
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

    if (!project) {
        return <div>Loading...</div>;
    }

    const isApproved = project.status !== 'DECLINED';
    const isInitiated = project.status === 'INITIATED';

    return (
        <div className="container mx-auto p-4">
            <button
                onClick={goBack}
                className="bg-gray-500 text-white px-4 py-2 rounded-lg mb-4 hover:bg-gray-600"
            >
                Go Back
            </button>

            <h2 className="text-2xl font-bold mb-4">Project Details</h2>

            <div className="bg-white p-4 rounded-lg shadow-lg mb-6">
                <h3 className="text-xl font-semibold mb-4">Project Overview</h3>
                <p><strong>Title:</strong> {project.title}</p>
                <p><strong>Status:</strong> {project.status}</p>
                <p><strong>Description:</strong> {project.description}</p>

                <button
                    onClick={handleApproveOrDisapprove}
                    className={`mt-4 px-4 py-2 rounded-lg mr-2 ${isApproved ? 'bg-red-500 text-white' : 'bg-green-500 text-white'} hover:opacity-75`}
                    disabled={isApproved && project.status === 'DECLINED'}
                >
                    {isApproved ? 'Decline' : 'Approve'}
                </button>
            </div>

            <div className="bg-white p-4 rounded-lg shadow-lg mb-6">
                <h3 className="text-xl font-semibold mb-4">Project Files</h3>

                <div>
                    <button
                        onClick={downloadProjectFile}
                        className={`px-4 py-2 rounded-lg ${
                            project.projectFile && !isInitiated ? 'bg-blue-500 text-white hover:bg-blue-600' : 'bg-gray-400 text-gray-700 cursor-not-allowed'
                        }`}
                        disabled={!project.projectFile || isInitiated}
                    >
                        {project.projectFile ? 'Download Project File' : 'Project File Not Uploaded'}
                    </button>
                </div>
                <div className="mb-4">
                    <h4 className="text-lg font-semibold mb-2">Upload Project File (Corrected Version)</h4>
                    <input
                        type="file"
                        onChange={(e) => setFile(e.target.files[0])}
                        className="block mb-2"
                    />
                    <button
                        onClick={handleFileUpload}
                        className="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600"
                    >
                        Upload
                    </button>
                    {uploadError && <p className="text-red-500 mt-2">{uploadError}</p>}
                    {uploadSuccess && <p className="text-green-500 mt-2">{uploadSuccess}</p>}
                </div>


            </div>

            <div className="bg-white p-4 rounded-lg shadow-lg mb-6">
                <h3 className="text-xl font-semibold mb-4">Latest Comment</h3>
                <p>{latestComment || 'No comments yet.'}</p>
            </div>

            <div className="bg-white p-4 rounded-lg shadow-lg mb-6">
                <h3 className="text-xl font-semibold mb-4">Latest Task</h3>
                <p><strong>Task:</strong> {latestTask || 'No tasks yet.'}</p>
                <p><strong>Status:</strong> {taskStatus || 'N/A'}</p>
            </div>

            <button
                onClick={project.progressReport ? viewProgressReport : createProgressReport}
                className={`bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 ${
                    (!isApproved || isInitiated) && 'cursor-not-allowed opacity-50'
                }`}
                disabled={!isApproved || isInitiated}
            >
                {project.progressReport ? 'View Progress Report' : 'Create Progress Report'}
            </button>
        </div>
    );
};

export default StudentProjectDetails;
