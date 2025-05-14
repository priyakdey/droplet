db = db.getSiblingDB("droplet");

db.createCollection("directories");
db.folders.createIndex({ ownerId: 1 });
db.folders.createIndex({ parentId: 1 });

db.createCollection("files");
db.files.createIndex({ ownerId: 1 });
db.files.createIndex({ parentId: 1 });

db.createUser({
    user: "droplet_user",
    pwd: "droplet_password",
    roles: [
        {
            role: "readWrite",
            db: "droplet"
        }
    ]
});
