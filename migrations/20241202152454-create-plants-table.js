'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.createTable('Plants', {
      idPlant: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true,
      },
      idNotification: {
        type: Sequelize.UUID, 
        allowNull: true, 
        defaultValue: Sequelize.UUIDV4,
      },
      id: { 
        type: Sequelize.UUID,
        allowNull: false,
        references: {
          model: 'Users', 
          key: 'id',
        },
        onDelete: 'CASCADE', 
      },
      namaPanggilanTanaman: {
        type: Sequelize.STRING,
        allowNull: false,
      },
      jenisTanaman: {
        type: Sequelize.STRING,
        allowNull: false,
      },
      fotoTanaman: {
        type: Sequelize.STRING,
        allowNull: false,
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
    });
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.dropTable('Plants');
  },
};
