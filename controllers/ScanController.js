// ScanController.js

require('dotenv').config();
const axios = require('axios');
const FormData = require('form-data');
const fs = require('fs');
const path = require('path');

const scanPlant = async (req, res) => {
    try {
        // Check if an image file is uploaded
        if (!req.file) {
            return res.status(400).json({ error: "No image file uploaded" });
        }

        // Get the uploaded image file
        console.log("Received file:", req.file);

        // Create form data to send to the Flask API
        const formData = new FormData();
        formData.append('file', fs.createReadStream(req.file.path)); // Append the file to the form data

        // Log the form data headers for debugging purposes
        console.log("Form data headers:", formData.getHeaders());

        // Send a POST request with the image to the Flask API
        const flaskResponse = await axios.post(process.env.SCANNING_MACHINE_API, formData, {
            headers: {
                ...formData.getHeaders(), // Include form data headers
            },
        });

        // Log the response from the Flask API
        console.log("Flask response:", flaskResponse.data);

        // Destructure the necessary fields from the Flask API response
        const { nama_tanaman, nama_penyakit, deskripsi_penyakit, penanganan_penyakit, pencegahan_penyakit, poin } = flaskResponse.data;

        // Send the extracted fields back to the client in the response
        res.json({
            nama_tanaman,
            nama_penyakit,
            deskripsi_penyakit,
            penanganan_penyakit,
            pencegahan_penyakit,
            poin,
        });
    } catch (error) {
        // Log any error that occurs during processing
        console.error("Error processing image:", error.message);
        // Return a 500 status with an error message
        res.status(500).json({
            error: "Failed to process the image",
            details: error.message,
        });
    }
};

// Export the scanPlant function for use in other files
module.exports = { scanPlant };
