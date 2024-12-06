'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.createTable('Notifications', {
      idNotification: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true,
      },
      idPlant: {
        type: Sequelize.UUID,
        allowNull: false,
        references: {
          model: 'Plants',
          key: 'idPlant',
        },
        onDelete: 'CASCADE',
      },
      idUser: {
        type: Sequelize.UUID,
        allowNull: false,
        references: {
          model: 'Users',
          key: 'id',
        },
        onDelete: 'CASCADE',
      },
      message: {
        type: Sequelize.STRING,
        allowNull: true, // Optional field
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
      updatedAt: { 
        allowNull: false, 
        type: Sequelize.DATE,
      },
      reminderIn: {
        type: Sequelize.DATE, // Use DATE type for reminders
        allowNull: true, // Allow null if it's optional
      },
    });
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.dropTable('Notifications');
  },
};
