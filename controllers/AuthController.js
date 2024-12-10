const { User } = require("../models");
const jwt = require("jsonwebtoken");
const signToken = (id) => {
  return jwt.sign({ id }, process.env.JWT_SECRET, {
    expiresIn: process.env.JWT_EXPIRES_IN,
  });
};

const createSendToken = (user, statusCode, res) => {
  const token = signToken(user.id);

  const cookieOption = {
    expire: new Date(
      Date.now() + process.env.JWT_COOKIE_EXPIRES_IN * 24 * 60 * 60 * 1000
    ),
    httpOnly: true,
  };

  res.cookie("jwt", token, cookieOption);

  user.password = undefined;

  res.status(statusCode).json({
    status: "Success",
    data: {
      user,
    },
  });
};

exports.registerUser = async (req, res) => {
  try {
    if (req.body.password != req.body.passwordConfirm) {
      return res.status(400).json({
        message: "Validasi Error",
        error: ["Password dan PasswordConfirm tidak sama"],
      });
    }

    const newUser = await User.create({
      name: req.body.name,
      email: req.body.email,
      password: req.body.password,
    });

    createSendToken(newUser, 201, res);
  } catch (error) {
    console.log(error);
    return res.status(400).json({
      message: "Validasi Error",
      error: error.errors.map((err) => err.message),
    });
  }
};

exports.loginUser = async (req, res) => {
  //fungsi validasi
  if (!req.body.email || !req.body.password) {
    return res.status(400).json({
      status: "Fail",
      message: "Error Validasi",
      error: "Please input email/password",
    });
  }

  //check jika users email yang dimasukkan di req sudah ada di DB
  //dan passwaord sudah benar yang di input di req
  const userData = await User.findOne({ where: { email: req.body.email } });

  if (
    !userData ||
    !(await userData.CorrectPassword(req.body.password, userData.password))
  ) {
    return res.status(400).json({
      status: "Fail",
      message: "Error Login",
      error: "Invalid Email or Password",
    });
  }

  //token di res pada saat login
  createSendToken(userData, 200, res);
};

exports.logoutUser = async (req, res) => {
  res.cookie("jwt", "", {
    httpOnly: true,
    expires: new Date(0),
  });

  res.status(200).json({
    message: "Logout Berhasil",
  });
};

exports.getMyUser = async (req, res) => {
  const currentUser = await User.findByPk(req.user.id);

  if (currentUser) {
    return res.status(200).json({
      id: currentUser.id,
      name: currentUser.name,
      email: currentUser.email,
      role_id: currentUser.role_id,
      points: currentUser.points,
      title: currentUser.title,
      last_check_in: currentUser.last_check_in,
    });
  }

  return res.status(404).json({
    message: "User tidak ditemukan",
  });
};
