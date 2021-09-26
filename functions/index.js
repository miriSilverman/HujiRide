/* eslint-disable max-len */
"use strict";

const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();


// Take the text parameter passed to this HTTP endpoint and insert it into
// Firestore under the path /messages/:documentId/original
// exports.addMessage = functions.https.onRequest(
//     async (req, res) => {
//       // Grab the text parameter.
//       const original = req.query.text;
//       // Push the new message into Firestore using the Firebase Admin SDK.
//       const writeResult = await admin.firestore().collection("messages").add({original: original});
//       // Send back a message that we've successfully written the message
//       res.json({result: `Message with ID: ${writeResult.id} added.`});
//     },
// );


exports.cleanOldRides = functions.https.onRequest(
    async (req, res) => {
      const fifteenMinsAgo = new Date(Date.now() - 15*60*1000);
      const now = admin.firestore.Timestamp.fromDate(fifteenMinsAgo);
      const l = await admin.firestore().collection("Rides")
          .where("timeStamp", "<", now).get();
      const oldSize = l.size;
      const batch = admin.firestore().batch();
      l.docs.forEach((ride) => {
        batch.delete(ride.ref);
      });
      await batch.commit();
      res.json({
        deleted: `${oldSize}`,
      });
    },
);

// Listens for new messages added to /messages/:documentId/original and creates an
// uppercase version of the message to /messages/:documentId/uppercase
// exports.makeUppercase = functions.firestore.document("/messages/{documentId}")
//     .onCreate((snap, context) => {
//       // Grab the current value of what was written to Firestore.
//       const original = snap.data().original;

//       // Access the parameter `{documentId}` with `context.params`
//       functions.logger.log("Uppercasing", context.params.documentId, original);

//       const uppercase = original.toUpperCase();

//       // You must return a Promise when performing asynchronous tasks inside a Functions such as
//       // writing to Firestore.
//       // Setting an 'uppercase' field in Firestore document returns a Promise.
//       return snap.ref.set({uppercase}, {merge: true});
//     });

exports.sendNotification = functions.https.onRequest(
    async (req, res) => {
      const id = req.query.id;
      const user = await admin.firestore().collection("Clients").doc(`${id}`).get();
      const token = user.data().fcmtoken;
      const message = {
        notification: {
          title: "TEST",
          body: "TEST",
        },
        data: {
          rideID: "none",
        },
        token: token,
      };
      const mRes = await admin.messaging().send(message);
      res.json({res: JSON.stringify(mRes)});
    },
);
