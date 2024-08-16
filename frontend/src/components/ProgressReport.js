// src/components/ProgressReport.js

import React from 'react';
import { motion } from 'framer-motion';

const ProgressReport = ({ progressData }) => {

    const progressDataSample = [
        {
            taskTitle: 'Initial Research',
            status: 'Completed',
            completionDate: '2024-06-30',
            progressPercentage: 100,
        },
        {
            taskTitle: 'Literature Review',
            status: 'In Progress',
            completionDate: null,
            progressPercentage: 70,
        },
        {
            taskTitle: 'Data Collection',
            status: 'Pending',
            completionDate: null,
            progressPercentage: 0,
        },
        {
            taskTitle: 'Analysis and Interpretation',
            status: 'Not Started',
            completionDate: null,
            progressPercentage: 0,
        },
        {
            taskTitle: 'Final Report Writing',
            status: 'Not Started',
            completionDate: null,
            progressPercentage: 0,
        },
    ];

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
                {progressDataSample.length > 0 ? (
                    progressDataSample.map((entry, index) => (
                        <div key={index} className="mb-6">
                            <h3 className="text-xl font-semibold mb-2 text-center">
                                {entry.taskTitle}
                            </h3>
                            <p className="text-center">
                                Status: <span className="font-bold">{entry.status}</span>
                            </p>
                            <p className="text-center">
                                Completion Date: {entry.completionDate}
                            </p>
                            <div className="w-full bg-gray-200 rounded-full h-2.5 mb-4 mt-2">
                                <div
                                    className={`bg-blue-500 h-2.5 rounded-full`}
                                    style={{ width: `${entry.progressPercentage}%` }}
                                ></div>
                            </div>
                            <p className="text-center text-sm text-gray-600">
                                {entry.progressPercentage}% completed
                            </p>
                        </div>
                    ))
                ) : (
                    <p className="text-center">No progress reports available.</p>
                )}
            </motion.div>
        </div>
    );
};

export default ProgressReport;
