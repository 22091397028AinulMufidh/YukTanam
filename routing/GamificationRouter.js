const express = require("express");
const router = express.Router();
const { authMiddleware } = require("../middleware/UserMiddleware");
const {
  completeTask,
  dailyCheckIn,
} = require("../controllers/GamificationController");

// /api/v1/gamification

//Complete a task
router.post("/complete-task/:id", authMiddleware, completeTask);

//Daily Check-In Task
router.post("/daily-check-in", authMiddleware, dailyCheckIn);

module.exports = router;
