const express = require("express");
const router = express.Router();
const { authMiddleware } = require("../middleware/UserMiddleware");
const { addPlant, readPlant, detailPlant, updatePlant, destroyPlant } = require("../controllers/PlantsController");
const { uploadOption } = require('../utils/fileUpload');

router.post("/", authMiddleware, uploadOption.single('image'), addPlant);
router.get('/', readPlant)
router.get('/:id', detailPlant)
router.put('/:id', uploadOption.single('image'), updatePlant)
router.delete('/:id', destroyPlant)

module.exports = router;
