const express = require("express");
const app = express();
const dotenv = require("dotenv");
const cors = require("cors");
const morgan = require("morgan");
const cookieParse = require("cookie-parser");
const plantRoutes = require("./routing/PlantRouter");
const AuthRouter = require("./routing/AuthRouter");
const GamificationRouter = require("./routing/GamificationRouter");
const { errorHandler, notFound } = require("./middleware/errorMiddleware");
const notificationScheduler = require('./scheduler/notificationScheduler');
const path = require('path')

dotenv.config();

//Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParse());

app.use(morgan("dev"));
app.use(cors());
app.use('/public/uploads', express.static(path.join(__dirname + '/public/uploads')))

//Routing
app.use("/api/v1/auth", AuthRouter);
app.use("/api/v1/gamification", GamificationRouter);
app.use("/api/v1/plants", plantRoutes);

app.use(notFound);
app.use(errorHandler);

notificationScheduler;

//Server
const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`server berjalan di port ${port}`);
});
