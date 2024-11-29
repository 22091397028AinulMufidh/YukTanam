const express = require("express");
const app = express();
const dotenv = require("dotenv");
const cors = require("cors");
const morgan = require("morgan");
const cookieParse = require("cookie-parser");
const AuthRouter = require("./routing/AuthRouter");
const { errorHandler, notFound } = require("./middleware/errorMiddleware");

dotenv.config();

//Middleware
app.use(express.json());
app.use(cookieParse());
//app.use((req, res, next) => {
//  req.requestTime = new Date().toISOString();
//  next();
//});
app.use(morgan("dev"));
app.use(cors());

//Routing
app.use("/api/v1/auth", AuthRouter);

app.use(notFound);
app.use(errorHandler);

//Server
const port = process.env.PORT;
app.listen(port, () => {
  console.log(`server berjalan di port ${port}`);
});
