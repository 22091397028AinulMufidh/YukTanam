const express = require("express");
const router = express.Router();
const { scanPlant } = require("../controllers/ScanController");
const multer = require("multer");

const upload = multer({ dest: 'uploads/'});

router.post('/plant', upload.single('file'), scanPlant);

module.exports = router;


