import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const AssignStudentPage = () => {
    const [matric, setMatric] = useState('');
    const [student, setStudent] = useState(null);
    const [error, setError] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [showSuccessModal, setShowSuccessModal] = useState(false);
    const navigate = useNavigate();
    const { reference } = useParams();  // Get supervisor reference from path

    // Fetch student details based on matric number
    const fetchStudentDetails = async () => {
        try {
            const response = await axios.get(`https://project-app-api.up.railway.app/api/v1/users/student/${matric}`);
            setStudent(response.data.data);
            setError('');
            setShowModal(true);
        } catch (error) {
            setError('Student not found');
            setStudent(null);
            setShowModal(false);
        }
    };

    // Handle the assignment of the student to the supervisor
    const handleAssignSupervisor = async () => {
        try {
            const response = await axios.post('https://project-app-api.up.railway.app/api/v1/users/assign-supervisor', {
                supervisor: reference,
                student: student.matric, // or use another identifier like student.id if available
            });
            if (response.data.success) {
                setShowModal(false);
                setShowSuccessModal(true);
            } else {
                setError('Failed to assign student to supervisor');
            }
        } catch (error) {
            setError('An error occurred while assigning the student');
        }
    };

    return (
        <div className="container mx-auto p-4">
            <h2 className="text-2xl font-bold mb-4">Assign Student to Supervisor</h2>

            <div className="mb-4">
                <label className="block text-sm font-medium text-gray-700">Student Matric Number</label>
                <input
                    type="text"
                    value={matric}
                    onChange={(e) => setMatric(e.target.value)}
                    className="mt-1 p-2 border rounded w-full"
                    placeholder="Enter student matric number"
                />
                <button
                    onClick={fetchStudentDetails}
                    className="bg-blue-500 text-white px-4 py-2 rounded mt-2 hover:bg-blue-600"
                >
                    Fetch Student
                </button>
            </div>

            {error && <p className="text-red-500">{error}</p>}

            {showModal && student && (
                <div className="fixed z-10 inset-0 overflow-y-auto">
                    <div className="flex items-center justify-center min-h-screen">
                        <div className="bg-white p-6 rounded-lg shadow-lg">
                            <h3 className="text-xl font-semibold mb-4">Confirm Student Assignment</h3>
                            <p><strong>Name:</strong> {student.name}</p>
                            <p><strong>Matric:</strong> {student.matric}</p>
                            {/* Add any other student details you want to show */}

                            <div className="mt-6 flex justify-end">
                                <button
                                    onClick={() => setShowModal(false)}
                                    className="bg-gray-300 text-black px-4 py-2 rounded mr-2 hover:bg-gray-400"
                                >
                                    Cancel
                                </button>
                                <button
                                    onClick={handleAssignSupervisor}
                                    className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
                                >
                                    Confirm Assignment
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            {showSuccessModal && (
                <div className="fixed z-10 inset-0 overflow-y-auto">
                    <div className="flex items-center justify-center min-h-screen">
                        <div className="bg-white p-6 rounded-lg shadow-lg">
                            <h3 className="text-xl font-semibold mb-4">Success</h3>
                            <p>Student has been successfully assigned to the supervisor.</p>
                            <div className="mt-6 flex justify-end">
                                <button
                                    onClick={() => navigate('/dashboard')}
                                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                                >
                                    Close
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            <button
                onClick={() => navigate('/dashboard')}
                className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600 mt-4"
            >
                Go Back
            </button>
        </div>
    );
};

export default AssignStudentPage;
