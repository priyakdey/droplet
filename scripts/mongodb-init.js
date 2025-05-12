db = db.getSiblingDB("droplet");

db.createCollection("directories");
db.directories.createIndex({ ownerId: 1 });
db.directories.createIndex({ parentId: 1 });

db.createCollection("files");
db.files.createIndex({ ownerId: 1 });
db.files.createIndex({ parentId: 1 });

db.createUser({
    user: "droplet_user",
    password: "droplet_password",
    roles: [
        {
            role: "readWrite",
            db: "droplet"
        }
    ]
});
