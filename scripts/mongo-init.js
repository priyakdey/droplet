db = db.getSiblingDB("droplet");

db.createCollection("profiles");
db.createCollection("directories");
db.createCollection("files");

db.directories.createIndex({ ownerId: 1 });
db.directories.createIndex({ parentId: 1 });

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
