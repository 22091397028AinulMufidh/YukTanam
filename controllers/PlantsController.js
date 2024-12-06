const { Plant, Notification } = require("../models");
const { v4: uuidv4 } = require('uuid');
const asyncHandler = require("../middleware/asyncHandle");
const fs = require('fs');
const{ Op } = require("sequelize")

exports.addPlant = async (req, res) => {
    try {
        let { namaPanggilanTanaman, jenisTanaman } = req.body;
        const id = req.user.id;
        const file = req.file;

        console.log("Request Body:", req.body);
        console.log("User ID:", id);
        console.log("Uploaded File:", file);

        if (!namaPanggilanTanaman || !jenisTanaman || !file) {
            return res.status(400).json({
                status: "Fail",
                message: "Silahkan masukkan Nama Panggilan Tanaman, Jenis Tanaman dan Foto Tanaman",
            });
        }

        const fileName = file.filename;
        const pathFile = `${req.protocol}://${req.get('host')}/public/uploads/${fileName}`;

        console.log("File Path:", pathFile);

        const newPlant = await Plant.create({
            idPlant: uuidv4(),
            namaPanggilanTanaman,
            jenisTanaman,
            fotoTanaman: pathFile,
            id
        });

        await Notification.create({ 
            idNotification: uuidv4(), 
            idPlant: newPlant.idPlant, 
            idUser: id, 
            message: "Remember to water your plant!", 
            reminderIn: new Date(new Date().getTime() + 24 * 60 * 60 * 1000) // Example: remind after 24 hours 
        });

        res.status(200).json({
            data: newPlant,
            status: "Success",
            message: "Berhasil menambahkan tanaman dan membuat notifikasi",
        });
    } catch (error) {
        console.error("Error:", error);
        res.status(400).json({
            status: "Fail",
            message: "Gagal menambahkan tanaman",
            error: error.message,
        });
    }
};

exports.readPlant = asyncHandler(async (req, res) => {

    const { search, limit, page } = req.query

    let plantData = ""

    if(search || limit || page){
        const pageData = page * 1 || 1
        const limitData = limit * 1 || 100
        const offsetData = (pageData - 1) * limitData
        const searchData = search || ""

        const plant = await Plant.findAndCountAll({
            limit : limitData,
            offset: offsetData,
            where: {
                namaPanggilanTanaman: {
                    [Op.like]: "%" + searchData + "%"
                }
            }
        });

        plantData = plant
    }else{
        const plant = await Plant.findAndCountAll();

        plantData = plant
    }

    return res.status(200).json({
        data: plantData
    });
});

exports.detailPlant = asyncHandler(async (req, res) => {
    const id = req.params.id;
    const plantData = await Plant.findByPk(id);

    if (!plantData) {
        return res.status(404).json({
            status: "Fail",
            message: "Plant ID tidak ditemukan"
        });
    }

    return res.status(200).json({
        data: plantData
    });
});

exports.updatePlant = asyncHandler(async (req, res) => {
    const idParams = req.params.id;
    let { namaPanggilanTanaman, jenisTanaman } = req.body;

    const plantData = await Plant.findByPk(idParams);

    if (!plantData) {
        return res.status(404).json({
            status: "Fail",
            message: "Plant ID tidak ditemukan"
        });
    }

    const file = req.file;

    // If a new file is uploaded
    if (file) {
        const nameImage = plantData.fotoTanaman.replace(`${req.protocol}://${req.get('host')}/public/uploads/`, "");
        const filePath = `./public/uploads/${nameImage}`;

        fs.unlink(filePath, (err) => {
            if (err) {
                return res.status(400).json({
                    status: "Fail",
                    message: "File tidak ditemukan",
                    error: err.message,
                });
            }
        });

        const fileName = file.filename;
        const pathFile = `${req.protocol}://${req.get('host')}/public/uploads/${fileName}`;

        plantData.fotoTanaman = pathFile;
    }

    plantData.namaPanggilanTanaman = namaPanggilanTanaman || plantData.namaPanggilanTanaman;
    plantData.jenisTanaman = jenisTanaman || plantData.jenisTanaman;

    await plantData.save();

    return res.status(200).json({
        message: "Berhasil update data tanaman",
        data: plantData
    });
});

exports.destroyPlant = asyncHandler(async (req, res) => {
    const idParams = req.params.id;
    const plantData = await Plant.findByPk(idParams);

    if (!plantData) {
        return res.status(404).json({
            status: "Fail",
            message: "Plant ID tidak ditemukan"
        });
    }

    const nameImage = plantData.fotoTanaman.replace(`${req.protocol}://${req.get('host')}/public/uploads/`, "");
    const filePath = `./public/uploads/${nameImage}`;

    fs.unlink(filePath, async (err) => {
        if (err) {
            return res.status(400).json({
                status: "Fail",
                message: "File tidak ditemukan",
                error: err.message,
            });
        }

        await Notification.destroy({ 
            where: { idPlant: idParams }
        });

        await plantData.destroy();

        return res.status(200).json({
            message: "Berhasil menghapus data tanaman dan notifikasi",
        });
    });
});
