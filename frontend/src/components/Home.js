// src/components/Home.js

import React from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';

const Home = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-blue-100 to-purple-200 p-4">
      <motion.h1
        className="text-3xl md:text-4xl lg:text-5xl font-bold mb-4 text-center text-gray-800"
        initial={{ opacity: 0, y: -50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
      >
        Welcome to the Project Monitoring System
      </motion.h1>
      <motion.p
        className="text-lg md:text-xl lg:text-2xl text-center text-gray-600 mb-8"
        initial={{ opacity: 0, y: 50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5, delay: 0.2 }}
      >
        Please select one of the options below to get started:
      </motion.p>
      <div className="flex flex-col md:flex-row justify-center md:space-x-4">
        <motion.div
          className="mb-4 md:mb-0 w-full md:w-auto"
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
        >
          
          <Link
            to={"/register"}
            className="bg-green-500 hover:bg-green-600 text-white font-bold py-3 px-6 rounded-lg shadow-lg w-full md:w-auto block text-center"
          >
            Create Account
          </Link>
        </motion.div>
        <motion.div
          className="w-full md:w-auto"
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
        >
          <Link
            to={'/login'}
            className="bg-purple-500 hover:bg-purple-600 text-white font-bold py-3 px-6 rounded-lg shadow-lg w-full md:w-auto block text-center"
          >
            Login
          </Link>
        </motion.div>
      </div>
    </div>
  );
};

export default Home;
