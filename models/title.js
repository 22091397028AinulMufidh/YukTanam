"use strict";
const { Model } = require("sequelize");

module.exports = (sequelize, DataTypes) => {
  class Title extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  }
  Title.init(
    {
      id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true,
      },
      title_name: {
        type: DataTypes.STRING,
        unique: true,
        allowNull: false,
      },
      required_points: {
        type: DataTypes.INTEGER,
        allowNull: false,
      },
    },
    {
      sequelize,
      modelName: "Title",
    }
  );
  return Title;
};
