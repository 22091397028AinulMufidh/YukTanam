"use strict";
const { Model } = require("sequelize");

module.exports = (sequelize, DataTypes) => {
    class Plant extends Model {
        static associate(models) {
            Plant.belongsTo(models.User, {
                foreignKey: 'id',
                as: 'user',
                onDelete: 'CASCADE',
                onUpdate: 'CASCADE',
            });
        }
    }

    Plant.init(
        {
            idPlant: {
                type: DataTypes.UUID,
                defaultValue: DataTypes.UUIDV4,
                primaryKey: true,
            },
            idNotification: {
                type: DataTypes.UUID,
                allowNull: true,
                defaultValue: DataTypes.UUIDV4,
            },
            namaPanggilanTanaman: {
                type: DataTypes.STRING,
                allowNull: false
            },
            jenisTanaman: {
                type: DataTypes.STRING,
                allowNull: false
            },
            fotoTanaman: {
                type: DataTypes.STRING,
                allowNull: false
            },
            id: {
                type: DataTypes.UUID,
                allowNull: false,
                references: {
                    model: "User",
                    key: "id"
                },
            },
        },
    {
        sequelize,
        modelName: "Plant",
    }
    );

    return Plant;
};
