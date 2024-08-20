// src/components/ProgressReport.js

import React, { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const ProgressReport = ({ onLogout, onBackToDashboard }) => {
    const [progressData, setProgressData] = useState([]);
    const [loading, setLoading] = useState(true);
    const user = JSON.parse(sessionStorage.getItem('user'));
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch progress report from server using project reference
        const fetchProgressData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/v1/progress-report/${user.project.reference}`);
                if (response.data.success) {
                    const stages = response.data.data.stages;

                    // Fetch level for each stage and update stage data
                    const stagesWithLevels = await Promise.all(stages.map(async (stage) => {
                        try {
                            const levelResponse = await axios.get(`http://localhost:8080/api/v1/progress-report/stages/level?stageReference=${stage.reference}`);
                            if (levelResponse.data.success) {
                                console.log(levelResponse.data.data)
                                return { ...stage, level: levelResponse.data.data };
                            } else {
                                console.error('Failed to fetch level data for stage:', stage.reference);
                                return { ...stage, level: 0 }; // Default level to 0 if failed
                            }
                        } catch (error) {
                            console.error('Error fetching level data:', error);
                            return { ...stage, level: 0 }; // Default level to 0 if failed
                        }
                    }));

                    setProgressData(stagesWithLevels);
                } else {
                    console.error('Failed to fetch progress data');
                }
            } catch (error) {
                console.error('Error fetching progress data:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProgressData();
    }, [user.project.reference]);

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200 p-4">
            <motion.h2
                className="text-2xl md:text-3xl lg:text-4xl font-semibold mb-4 text-center text-gray-700"
                initial={{ opacity: 0, y: 50 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.5, delay: 0.2 }}
            >
                Progress Report
            </motion.h2>
            <motion.div
                className="bg-white p-6 rounded-lg shadow-lg w-full md:w-3/4 lg:w-1/2 mb-8"
                initial={{ opacity: 0, scale: 0.8 }}
                animate={{ opacity: 1, scale: 1 }}
                transition={{ duration: 0.5, delay: 0.3 }}
            >
                {loading ? (
                    <p className="text-center">Loading progress report...</p>
                ) : progressData.length > 0 ? (
                    progressData.map((stage, index) => (
                        <div key={index} className="mb-6">
                            <h3 className="text-xl font-semibold mb-2 text-center">
                                {stage.name}
                            </h3>
                            <p className="text-center">
                                Status: <span className="font-bold">{stage.completed ? 'Completed' : 'In Progress'}</span>
                            </p>
                            <p className="text-center">
                                Completion Date: {stage.completionDate || 'In Progress'}
                            </p>
                            <div className="w-full bg-gray-200 rounded-full h-2.5 mb-4 mt-2">
                                <div
                                    className={`bg-blue-500 h-2.5 rounded-full`}
                                    style={{ width: `${stage.level}%` }}
                                ></div>
                            </div>
                            <p className="text-center text-sm text-gray-600">
                                {stage.level}% completed
                            </p>
                            <button
                                className="mt-4 bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
                                onClick={() => navigate(`/dashboard/progress-report/tasks/${stage.reference}`)}
                            >
                                View Tasks
                            </button>
                        </div>
                    ))
                ) : (
                    <p className="text-center">No progress reports available.</p>
                )}
            </motion.div>
            <div className="flex space-x-4">
                <button
                    className="bg-gray-500 text-white py-2 px-4 rounded hover:bg-gray-600"
                    onClick={onBackToDashboard}
                >
                    Back to Dashboard
                </button>
                <button
                    className="bg-red-500 text-white py-2 px-4 rounded hover:bg-red-600"
                    onClick={onLogout}
                >
                    Logout
                </button>
            </div>
        </div>
    );
};

export default ProgressReport;
