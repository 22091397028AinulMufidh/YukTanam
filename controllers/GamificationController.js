const asyncHandle = require("../middleware/asyncHandle");
const { Tasks } = require("../models");
const { User } = require("../models");

//Completing A Task
exports.completeTask = async (req, res) => {
  try {
    const user = req.user.id;
    const CurrentUser = await User.findByPk(req.user.id);
    if (!CurrentUser) {
      return res.status(404).json({
        error: "User tiddak ditemukan!",
      });
    }

    const task = await Tasks.findByPk(req.params.id);

    const updatedPoints = CurrentUser.points + task.reward_points;

    CurrentUser.points = updatedPoints;
    CurrentUser.title = getUpdatedTitle(updatedPoints);
    //update the user's points and title
    await CurrentUser.save();

    return res.status(200).json({
      status: "Success",
      message: "Task telah berhasil diselesaikan! Reward telah ditambahkan.",
      user: {
        id: user,
        name: CurrentUser.name,
        points: CurrentUser.points,
        title: CurrentUser.title,
      },
    });
  } catch (error) {
    console.log(error);
    res.status(500).json({
      status: "Fail",
      error: "Server Down",
    });
  }
};

// Helper function to get updated title based on points
const getUpdatedTitle = (points) => {
  if (points >= 1000) return "Legenda YukTanam";
  if (points >= 750) return "Pahlawan Keberlanjutan";
  if (points >= 500) return "Raja/Ratu Kebun";
  if (points >= 350) return "Ahli Tanaman";
  if (points >= 200) return "Penyelamat Lingkungan";
  if (points >= 100) return "Sahabat Alam";
  if (points >= 50) return "Tukang Kebun Rajin";
  if (points >= 10) return "Pemula Taman";
  return "Ingin Punya Taman";
};

//Daily Check-In Task
exports.dailyCheckIn = async (req, res) => {
  const dailyCheckInPoints = 5; //points awarded for daily check-in

  try {
    const user = req.user.id;
    const CurrentUser = await User.findByPk(req.user.id);
    if (!CurrentUser) {
      return res.status(404).json({
        error: "User tiddak ditemukan!",
      });
    }

    const today = new Date();
    const lastCheckIn = CurrentUser.last_check_in
      ? new Date(CurrentUser.last_check_in)
      : null;

    if (lastCheckIn && lastCheckIn.toDateString() === today.toDateString()) {
      return res.status(200).json({
        id: CurrentUser.id,
        username: CurrentUser.username,
        points: CurrentUser.points,
        title: CurrentUser.title,
        last_check_in: lastCheckIn,
      });
    }

    const updatedPoints = CurrentUser.points + dailyCheckInPoints;

    CurrentUser.points = updatedPoints;
    CurrentUser.title = getUpdatedTitle(updatedPoints);
    CurrentUser.last_check_in = today;
    //update the user's points and title
    await CurrentUser.save();

    return res.status(200).json({
      status: "Success",
      message: "Daily Check-In telah diselesaikan! Reward telah ditambahkan.",
      user: {
        id: user,
        name: CurrentUser.name,
        points: CurrentUser.points,
        title: CurrentUser.title,
      },
    });
  } catch (error) {
    console.log(error);
    res.status(500).json({
      status: "Fail",
      error: "Server Down",
    });
  }
};

//get all tasks
exports.getAllTasks = async (req, res) => {
  try {
    const tasks = await Tasks.findAll();

    return res.status(200).json({
      status: "Success",
      data: tasks,
    });
  } catch (error) {
    return res.status(500).json({
      status: "Fail",
      error: "Server Down",
    });
  }
};
