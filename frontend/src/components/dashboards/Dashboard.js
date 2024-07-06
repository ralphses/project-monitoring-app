// src/components/Dashboard.js

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import StudentDashboard from './Student';
import AdminDashboard from './Admin';
import SupervisorDashboard from './Supervisor';
import { motion } from 'framer-motion';

const Dashboard = () => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const username = 'ralph';
    if (username) {
      axios.get(`http://localhost:8080/api/v1/users/${username}`)
        .then(response => {
            // console.log(response.data.data);
          setUser(response.data.data);
        })
        .catch(error => {
          console.error('Error fetching user data:', error);
        });
    }
  }, []);

  if (!user) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <p>Loading...</p>
      </div>
    );
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200 p-4">
      <motion.h1
        className="text-3xl md:text-4xl lg:text-5xl font-bold mb-4 text-center text-gray-800"
        initial={{ opacity: 0, y: -50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
      >
        Welcome, {user.name}
      </motion.h1>
      {user.role === 'STUDENT' && <StudentDashboard />}
      {user.role === 'ADMIN' && <AdminDashboard />}
      {user.role !== 'STUDENT' && user.role !== 'ADMIN' && <SupervisorDashboard />}
    </div>
  );
};

export default Dashboard;
