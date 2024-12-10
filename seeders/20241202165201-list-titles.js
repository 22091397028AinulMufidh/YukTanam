"use strict";
const { v4 } = require("uuid");

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.bulkInsert(
      "Title",
      [
        {
          id: v4(),
          title_name: "Ingin Punya Taman",
          required_points: 0,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          id: v4(),
          title_name: "Pemula Taman",
          required_points: 10,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          id: v4(),
          title_name: "Tukang Kebun Rajin",
          required_points: 50,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          id: v4(),
          title_name: "Sahabat Alam",
          required_points: 100,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          id: v4(),
          title_name: "Penyelamat Lingkungan",
          required_points: 200,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          id: v4(),
          title_name: "Ahli Tanaman",
          required_points: 350,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          id: v4(),
          title_name: "Raja/Ratu Kebun",
          required_points: 500,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          id: v4(),
          title_name: "Pahlawan Keberlanjutan",
          required_points: 750,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          id: v4(),
          title_name: "Legenda YukTanam",
          required_points: 1000,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
      ],
      {}
    );
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete("Title", null, {});
  },
};
