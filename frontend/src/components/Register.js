// src/components/RegisterPage.js

import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { motion } from 'framer-motion';

const RegisterPage = () => {
  const [name, setName] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [showModal, setShowModal] = useState(false);

  const validateForm = () => {
    const errors = {};

    if (!name.trim()) {
      errors.name = 'Name is required';
    }
    if (!username.trim()) {
      errors.username = 'Username is required';
    }
    if (email.trim() && !/\S+@\S+\.\S+/.test(email)) {
      errors.email = 'Email address is invalid';
    }
    if (!phone.trim()) {
      errors.phone = 'Phone number is required';
    } else if (!/^\d{10}$/.test(phone)) {
      errors.phone = 'Phone number is invalid';
    }
    if (!password.trim()) {
      errors.password = 'Password is required';
    }
    if (password !== confirmPassword) {
      errors.confirmPassword = 'Passwords do not match';
    }
    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePhoneChange = (e) => {
    setPhone(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const isValid = validateForm();
    if (isValid) {
      try {
        // Prepare data to send
        const dataToSend = {
          name,
          username,
          email,
          phone,
          password,
        };

        console.log(dataToSend);

        // Send data to the backend
        const response = await axios.post('http://localhost:8080/api/v1/register', dataToSend);

        if (response.data.success) {
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

  // Close modal
  const closeModal = () => {
    setShowModal(false);
    window.location.href = "/login";
  };

  return (
    <div className="container mx-auto mt-8 p-4">
      <motion.h1
        className="text-3xl font-bold mb-4 text-center text-gray-800"
        initial={{ opacity: 0, y: -50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
      >
        Register
      </motion.h1>
      <motion.div
        className="max-w-md mx-auto bg-white p-8 rounded-lg shadow-lg"
        initial={{ opacity: 0, scale: 0.8 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ duration: 0.5, delay: 0.2 }}
      >
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="name" className="block text-gray-700 font-semibold mb-2">Name:</label>
            <input
              type="text"
              id="name"
              className={`w-full px-3 py-2 border rounded-md ${errors.name && 'border-red-500'}`}
              value={name}
              onChange={handleNameChange}
            />
            {errors.name && <p className="text-red-500 mt-1">{errors.name}</p>}
          </div>
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
            <label htmlFor="email" className="block text-gray-700 font-semibold mb-2">Email (optional):</label>
            <input
              type="email"
              id="email"
              className={`w-full px-3 py-2 border rounded-md ${errors.email && 'border-red-500'}`}
              value={email}
              onChange={handleEmailChange}
            />
            {errors.email && <p className="text-red-500 mt-1">{errors.email}</p>}
          </div>
          <div className="mb-4">
            <label htmlFor="phone" className="block text-gray-700 font-semibold mb-2">Phone Number:</label>
            <input
              type="text"
              id="phone"
              className={`w-full px-3 py-2 border rounded-md ${errors.phone && 'border-red-500'}`}
              value={phone}
              onChange={handlePhoneChange}
            />
            {errors.phone && <p className="text-red-500 mt-1">{errors.phone}</p>}
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
          <div className="mb-4">
            <label htmlFor="confirmPassword" className="block text-gray-700 font-semibold mb-2">Confirm Password:</label>
            <input
              type="password"
              id="confirmPassword"
              className={`w-full px-3 py-2 border rounded-md ${errors.confirmPassword && 'border-red-500'}`}
              value={confirmPassword}
              onChange={handleConfirmPasswordChange}
            />
            {errors.confirmPassword && <p className="text-red-500 mt-1">{errors.confirmPassword}</p>}
          </div>
          {errors.server && <p className="text-red-500 mb-4">{errors.server}</p>}
          <div className="flex justify-between items-center">
            <motion.button
              type="submit"
              className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 rounded-lg"
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              Register
            </motion.button>
            <Link to="/login" className="text-blue-500 font-semibold">Login</Link>
          </div>
        </form>
      </motion.div>

      {showModal && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded-lg shadow-lg text-center">
            <p className="text-gray-800 mb-4">Registration successful!</p>
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

export default RegisterPage;
