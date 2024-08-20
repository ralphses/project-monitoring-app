import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import StudentDashboard from './Student';
import AdminDashboard from './Admin';
import SupervisorDashboard from './Supervisor';
import { motion } from 'framer-motion';

const Dashboard = () => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch user from session storage
        const storedUser = sessionStorage.getItem('user');

        if (storedUser) {
            const parsedUser = JSON.parse(storedUser);
            setUser(parsedUser);

            console.log(parsedUser);

            // Check if the user is a student and has no project
            if (parsedUser.role === 'STUDENT' && !parsedUser.project) {
                navigate('/dashboard/create-project');
            }
        }
    }, [navigate]);

    if (!user) {
        return (
            <div className="flex items-center justify-center min-h-screen">
                <p>Loading...</p>
            </div>
        );
    }

    const renderDashboard = () => {
        switch (user.role) {
            case 'STUDENT':
                return <StudentDashboard user={user} />;
            case 'ADMIN':
                return <AdminDashboard user={user} />;
            default:
                return <SupervisorDashboard user={user} />;
        }
    };

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
            {renderDashboard()}
        </div>
    );
};

export default Dashboard;
