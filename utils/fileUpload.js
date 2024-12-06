const multer = require('multer');

const FILE_TYPE = {
    'image/png': 'png',
    'image/jpg': 'jpg',
    'image/jpeg': 'jpeg'
};

const storageFile = multer.diskStorage({
    destination: function (req, file, cb) {
        const isValidformat = FILE_TYPE[file.mimetype];
        let uploadError = new Error('Invalid Image Type');

        if (isValidformat) {
            uploadError = null;
        }

        cb(uploadError, 'public/uploads');
    },
    filename: function (req, file, cb) {
        const extension = FILE_TYPE[file.mimetype];
        const uniquefileImage = `${file.fieldname}-${Date.now()}.${extension}`
        cb(null, uniquefileImage);
    }
});

exports.uploadOption = multer({ storage: storageFile });
