const cron = require('node-cron');
const { Notification, User, Plant } = require('../models');
const { sendNotification } = require('../utils/notificationService'); 
const { Op } = require('sequelize');

// Schedule a task to run every hour
cron.schedule('0 * * * *', async () => {
    console.log('Running a task every hour to check for notifications');
    
    const notifications = await Notification.findAll({
        where: {
            reminderIn: {
                [Op.lte]: new Date()
            }
        }
    });

    notifications.forEach(async (notification) => {
        // Fetch user and plant data for detailed notification
        const user = await User.findByPk(notification.idUser);
        const plant = await Plant.findByPk(notification.idPlant);

        // Construct the notification message
        const message = notification.message || `Time to water your plant ${plant.namaPanggilanTanaman}`;

        // Send notification
        await sendNotification(user, message);

        // Log the notification sent
        console.log(`Sending notification to ${user.email}: ${message}`);
    });
});
