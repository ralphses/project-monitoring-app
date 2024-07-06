// src/components/StudentDashboard.js

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';

const StudentDashboard = () => {
  const [projectTitle, setProjectTitle] = useState('');
  const [latestTask, setLatestTask] = useState(null);
  const [latestComment, setLatestComment] = useState('');

  useEffect(() => {
    // Fetch project title, latest task, and latest comment from the server
    const fetchDashboardData = async () => {
      const username = sessionStorage.getItem('username');
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/student/${username}/dashboard`);
        const { projectTitle, latestTask, latestComment } = response.data;
        setProjectTitle(projectTitle);
        setLatestTask(latestTask);
        setLatestComment(latestComment);
      } catch (error) {
        console.error('Error fetching dashboard data:', error);
      }
    };

    fetchDashboardData();
  }, []);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200 p-4">
      <motion.h2
        className="text-2xl md:text-3xl lg:text-4xl font-semibold mb-4 text-center text-gray-700"
        initial={{ opacity: 0, y: 50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5, delay: 0.2 }}
      >
        Project Topic: {projectTitle}
      </motion.h2>
      <motion.div
        className="bg-white p-6 rounded-lg shadow-lg w-full md:w-3/4 lg:w-1/2 mb-8"
        initial={{ opacity: 0, scale: 0.8 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ duration: 0.5, delay: 0.3 }}
      >
        <h3 className="text-xl font-semibold mb-2 text-center">Latest Task</h3>
        {latestTask ? (
          <p className="text-center">{latestTask.name} ({latestTask.status})</p>
        ) : (
          <p className="text-center">No tasks available.</p>
        )}
      </motion.div>
      <motion.div
        className="bg-white p-6 rounded-lg shadow-lg w-full md:w-3/4 lg:w-1/2 mb-8"
        initial={{ opacity: 0, scale: 0.8 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ duration: 0.5, delay: 0.4 }}
      >
        <h3 className="text-xl font-semibold mb-2 text-center">Latest Comment from Supervisor</h3>
        {latestComment ? (
          <p className="text-center">{latestComment}</p>
        ) : (
          <p className="text-center">No comments available.</p>
        )}
      </motion.div>
      <div className="flex flex-col md:flex-row justify-center md:space-x-4 w-full md:w-3/4 lg:w-1/2">
        <motion.div
          className="mb-4 md:mb-0 w-full md:w-auto"
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
        >
          <Link
            to="/tasks"
            className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 rounded-lg shadow-lg w-full md:w-auto block text-center"
          >
            View Tasks
          </Link>
        </motion.div>
        <motion.div
          className="mb-4 md:mb-0 w-full md:w-auto"
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
        >
          <Link
            to="/progress-report"
            className="bg-green-500 hover:bg-green-600 text-white font-bold py-3 px-6 rounded-lg shadow-lg w-full md:w-auto block text-center"
          >
            Progress Report
          </Link>
        </motion.div>
        <motion.div
          className="w-full md:w-auto"
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
        >
          <Link
            to="/comments"
            className="bg-purple-500 hover:bg-purple-600 text-white font-bold py-3 px-6 rounded-lg shadow-lg w-full md:w-auto block text-center"
          >
            View Comments
          </Link>
        </motion.div>
      </div>
    </div>
  );
};

export default StudentDashboard;
