"use strict";
const { v4 } = require("uuid");

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.bulkInsert(
      "Tasks",
      [
        {
          task_id: v4(),
          task_name: "Deteksi Tanaman",
          task_description: "Melakukan deteksi tanaman sebanyak 1 kali",
          reward_points: 10,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          task_id: v4(),
          task_name: "Tambahkan tanaman",
          task_description: "Tambahkan sebanyak 1 tanaman di menu beranda",
          reward_points: 30,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          task_id: v4(),
          task_name: "Tanaman favorite",
          task_description: "Tambahkan sebanyak 1 tanaman favorite Anda",
          reward_points: 15,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
      ],
      {}
    );
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete("Tasks", null, {});
  },
};
