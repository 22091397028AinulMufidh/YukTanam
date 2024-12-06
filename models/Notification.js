"use strict";
const { Model } = require("sequelize");

module.exports = (sequelize, DataTypes) => {
    class Notification extends Model {
        static associate(models) {
            Notification.belongsTo(models.Plant, {
                foreignKey: 'idPlant',
                as: 'plant',
                onDelete: 'CASCADE',
            });
            Notification.belongsTo(models.User, {
                foreignKey: 'idUser',
                as: 'user',
                onDelete: 'CASCADE',
            });
        }
    }

    Notification.init(
        {
            idNotification: {
                type: DataTypes.UUID,
                defaultValue: DataTypes.UUIDV4,
                primaryKey: true,
            },
            idPlant: {
                type: DataTypes.UUID,
                allowNull: false,
            },
            idUser: {
                type: DataTypes.UUID,
                allowNull: false,
            },
            message: {
                type: DataTypes.STRING,
                allowNull: true,
            },
            reminderIn: {
                type: DataTypes.DATE,
                allowNull: true,
            },
        },
        {
            sequelize,
            modelName: "Notification",
            timestamps: true // Ensure timestamps are enabled
        }
    );

    return Notification;
};
