// src/components/CreateProject.js

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const CreateProject = () => {
    const navigate = useNavigate();
    const user = JSON.parse(sessionStorage.getItem('user'));
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [error, setError] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!title || !description) {
            setError('Both title and description are required.');
            return;
        }

        const projectDetails = {
            userReference: user.reference, // Matches CreateProjectRequest structure
            title,
            description,
        };

        axios.post('http://localhost:8080/api/v1/project', projectDetails)
            .then(response => {
                // Update the user object in session storage with the new project
                const user = response.data.data;
                console.log(user)
                sessionStorage.setItem('user', JSON.stringify(user));
                navigate('/dashboard');
            })
            .catch(error => {
                setError('Failed to create project. Please try again.');
            });
    };

    const handleLogout = () => {
        sessionStorage.clear();
        navigate('/');
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-green-100 to-blue-200 p-4">
            <div className="bg-white p-6 rounded-lg shadow-lg w-full md:w-3/4 lg:w-1/2">
                <h2 className="text-2xl font-semibold mb-4 text-center text-gray-700">Create Your Project</h2>
                {error && <p className="text-red-500 mb-4 text-center">{error}</p>}
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2" htmlFor="title">
                            Project Title
                        </label>
                        <input
                            type="text"
                            id="title"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2" htmlFor="description">
                            Project Description
                        </label>
                        <textarea
                            id="description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className="w-full px-3 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-lg shadow-lg transition-all duration-300 ease-in-out transform hover:scale-105"
                    >
                        Create Project
                    </button>
                </form>
                <button
                    onClick={handleLogout}
                    className="w-full mt-4 bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded-lg shadow-lg transition-all duration-300 ease-in-out transform hover:scale-105"
                >
                    Logout
                </button>
            </div>
        </div>
    );
};

export default CreateProject;
