const jwt = require("jsonwebtoken");
const { User, Role } = require("../models");

exports.authMiddleware = async (req, res, next) => {
  //fungsi jika di header kita masukan token atau tidak
  let token;

  token = req.cookies.jwt;

  if (
    req.headers.authorization &&
    req.headers.authorization.startsWith("Bearer")
  ) {
    token = req.headers.authorization.split(" ")[1];
  }

  if (!token) {
    return next(
      res.status(401).json({
        status: 401,
        message: "anda belum bisa login/register token tidak ditemukan",
      })
    );
  }

  //console.log(token);
  //decode verikasi token
  let decoded;
  try {
    decoded = await jwt.verify(token, process.env.JWT_SECRET);
  } catch (error) {
    console.error("JWT verification failed:", error);
    return next(
      res.status(401).json({
        error: error,
        message: "Token yang dimasukkan tidak ditemukan/tidak ada",
      })
    );
  }

  //ambil data user berdasarkan kondisi decoded
  const currentUser = await User.findByPk(decoded.id);
  if (!currentUser) {
    return next(
      res.status(401).json({
        status: 401,
        message: "User sudah terhapus, token sudah tidak bisa digunakan",
      })
    );
  }
  //console.log(currentUser);

  req.user = currentUser;

  next();
};

exports.permissionUser = (...roles) => {
  return async (req, res, next) => {
    const rolesData = await Role.findByPk(req.user.role_id);

    const roleName = rolesData.name;

    if (!roles.includes(roleName)) {
      return next(
        res.status(403).json({
          status: 403,
          error: "Anda tidak dapat mengakses halaman ini!!!",
        })
      );
    }

    next();
  };
};
