const express = require("express");
const bodyParser = require("body-parser");
const mysql = require("mysql");
const bcrypt = require("bcrypt");
const { v4: uuidv4, parse: uuidParse } = require("uuid");
const app = express();
app.use(
    bodyParser.urlencoded({
        extended: false,
    })
);

app.use(bodyParser.json());

const db_config = {
    host: "localhost",
    user: "kotlin",
    password: "kotlinpass",
    database: "voters_app",
};

const dbPool = mysql.createPool(db_config);

app.post("/signup", async (req, res) => {
    var email = req.body.email;
    var password = await bcrypt.hash(req.body.password, 10);
    var firstName = req.body.firstName;
    var middleName = req.body.middleName;
    var lastName = req.body.lastName;

    var isExisting = await checkIfExists(email);

    if (isExisting) return res.status(401).send("Email already registered.");

    let insertQry = "INSERT INTO ??(??) VALUES (UUID_TO_BIN(?), ?)";

    let query = mysql.format(insertQry, [
        "app_users",
        ["id", "first_name", "middle_name", "last_name", "email", "passwd"],
        uuidv4(),
        [firstName, middleName, lastName, email, password],
    ]);

    try {
        await db_query(query, dbPool);
        res.status(201).send({
            message: "User created.",
        });
    } catch (err) {
        console.log(`Error Inserting Data: ${err}`);
        res.status(400).send({
            message: "Failed to create user.",
        });
    }
});

app.post("/login", async (req, res) => {
    var email = req.body.email;
    var password = req.body.password;

    selectQry = "SELECT * FROM ?? WHERE ?? LIKE ?";

    let query = mysql.format(selectQry, ["app_users", "email", email]);
    try {
        var results = await db_query(query, dbPool);
        if (results == "")
            return res.status(200).send({
                message: "Invalid Credentials.",
            });
        bcrypt.compare(password, results[0].passwd, function (err, result) {
            if (result == true)
                return res.status(200).send({
                    message: `${results[0].first_name} ${results[0].last_name} logged in.`,
                });
            return res.status(401).send({
                message: "Invalid Credentials.",
            });
        });
    } catch (err) {
        console.log(`Caught error while Logging in: ${err}`);
    }
});

async function checkIfExists(email) {
    selectQry = "SELECT ?? FROM ?? WHERE ?? LIKE ?";

    let query = mysql.format(selectQry, ["email", "app_users", "email", email]);
    try {
        var results = await db_query(query, dbPool);
        if (results == "") return false;
        return true;
    } catch (err) {
        console.log(`Caught Error while querying database: ${err}`);
    }
}

async function db_query(query, pool) {
    const rows = await new Promise((resolve, reject) => {
        pool.query(query, (err, rows, fields) => {
            if (err) reject(err);
            resolve(rows);
        });
    });

    return rows;
}

app.listen(5000, () => {
    console.log("Server running on PORT 5000");
});
