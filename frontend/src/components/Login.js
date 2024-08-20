// src/components/LoginPage.js

import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'; // Import useNavigate
import axios from 'axios';
import { motion } from 'framer-motion';

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate(); // Initialize useNavigate

  const validateForm = () => {
    const errors = {};

    if (!username.trim()) {
      errors.username = 'Username is required';
    }
    if (!password.trim()) {
      errors.password = 'Password is required';
    }
    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const isValid = validateForm();
    if (isValid) {
      try {
        // Prepare data to send
        const dataToSend = {
          username,
          password,
        };


        // Send data to the backend
        const response = await axios.post('http://localhost:8080/api/v1/auth/login', dataToSend);

        if (response.data.success) {
          // Store user data in session storage
          sessionStorage.setItem('user', JSON.stringify(response.data.data));
          setShowModal(true);
        } else {
          setErrors({ server: response.data.message });
        }
      } catch (error) {
        console.error('Error while sending data:', error);
        setErrors({ server: 'An error occurred while processing your request' });
      }
    }
  };

  // Close modal and redirect to dashboard
  const closeModal = () => {
    setShowModal(false);
    navigate('/dashboard'); // Redirect to the dashboard
  };

  return (
      <div className="container mx-auto mt-8 p-4">
        <motion.h1
            className="text-3xl font-bold mb-4 text-center text-gray-800"
            initial={{ opacity: 0, y: -50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
        >
          Login
        </motion.h1>
        <motion.div
            className="max-w-md mx-auto bg-white p-8 rounded-lg shadow-lg"
            initial={{ opacity: 0, scale: 0.8 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.5, delay: 0.2 }}
        >
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label htmlFor="username" className="block text-gray-700 font-semibold mb-2">Username:</label>
              <input
                  type="text"
                  id="username"
                  className={`w-full px-3 py-2 border rounded-md ${errors.username && 'border-red-500'}`}
                  value={username}
                  onChange={handleUsernameChange}
              />
              {errors.username && <p className="text-red-500 mt-1">{errors.username}</p>}
            </div>
            <div className="mb-4">
              <label htmlFor="password" className="block text-gray-700 font-semibold mb-2">Password:</label>
              <input
                  type="password"
                  id="password"
                  className={`w-full px-3 py-2 border rounded-md ${errors.password && 'border-red-500'}`}
                  value={password}
                  onChange={handlePasswordChange}
              />
              {errors.password && <p className="text-red-500 mt-1">{errors.password}</p>}
            </div>
            {errors.server && <p className="text-red-500 mb-4">{errors.server}</p>}
            <div className="flex justify-between items-center">
              <motion.button
                  type="submit"
                  className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 rounded-lg"
                  whileHover={{ scale: 1.05 }}
                  whileTap={{ scale: 0.95 }}
              >
                Login
              </motion.button>
              <Link to="/register" className="text-blue-500 font-semibold">Register</Link>
            </div>
          </form>
        </motion.div>

        {showModal && (
            <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
              <div className="bg-white p-6 rounded-lg shadow-lg text-center">
                <p className="text-gray-800 mb-4">Login successful!</p>
                <button
                    className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-lg"
                    onClick={closeModal}
                >
                  Close
                </button>
              </div>
            </div>
        )}
      </div>
  );
};

export default LoginPage;
