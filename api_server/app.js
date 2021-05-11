const express = require("express");
const bodyParser = require("body-parser");
const mysql = require("mysql");
const bcrypt = require("bcrypt");
const { v4: uuidv4, parse: uuidParse } = require("uuid");
const e = require("express");
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
    var firstName = req.body.first_name;
    var middleName = req.body.middle_name;
    var lastName = req.body.last_name;

    var rows = await getUserByEmail(email, "app_users");
    if (rows != "")
        return res.status(400).send({
            message: "Email already registered.",
        });

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

    var rows = await getUserByEmail(email, "app_users");

    if (rows == "")
        return res.status(401).send({
            message: "Invalid Credentials.",
        });

    rows[0].id = Bin2HexUUID(rows[0].id);

    bcrypt.compare(password, rows[0].passwd, function (err, result) {
        if (result == true) {
            delete rows[0]["passwd"];
            return res.status(200).send({
                profile: rows[0],
            });
        }

        return res.status(401).send({
            message: "Invalid Credentials.",
        });
    });
});

app.post("/voter/register", async (req, res) => {
    var firstName = req.body.first_name;
    var middleName = req.body.middle_name;
    var lastName = req.body.last_name;
    var email = req.body.email;
    var birthDate = req.body.birth_date;
    var birthProvince = req.body.birth_province;
    var birthCity = req.body.birth_city;
    var civilStatus = req.body.civil_status;
    var sex = req.body.sex;
    var street = req.body.street;
    var barangay = req.body.barangay;
    var city = req.body.city;
    var province = req.body.province;
    var yearsInCity = req.body.years_in_city;
    var yearsInPH = req.body.years_in_ph;
    var appUserId = req.body.app_user_id;

    var rows = await getUserById(appUserId, "voters");
    if (rows != "")
        return res.status(400).send({
            message: "Voter already signed up.",
        });

    let insertQry = "INSERT INTO ??(??) VALUES (UUID_TO_BIN(?), ?)";

    let query = mysql.format(insertQry, [
        "voters",
        [
            "app_user_id",
            "first_name",
            "middle_name",
            "last_name",
            "email",
            "birth_date",
            "birth_province",
            "birth_city",
            "civil_status",
            "sex",
            "street",
            "barangay",
            "city",
            "province",
            "years_in_city",
            "years_in_ph",
            "isRegistered",
            "voter_id_number",
            "precinct_number",
            "registration_status",
        ],
        appUserId,
        [
            firstName,
            middleName,
            lastName,
            email,
            birthDate,
            birthProvince,
            birthCity,
            civilStatus,
            sex,
            street,
            barangay,
            city,
            province,
            yearsInCity,
            yearsInPH,
            false,
            null,
            null,
            "PENDING",
        ],
    ]);

    try {
        await db_query(query, dbPool);
        res.status(201).send({
            message: "Voter Application Successful.",
        });
    } catch (err) {
        res.status(400).send({
            message: `Voter Application Failed: ${err} `,
        });
    }
});

app.get("/voter/:id", async (req, res) => {
    var voterId = req.params.id;

    try {
        var voterInfo = await getUserById(voterId, "voters");
        if (voterInfo == "")
            return res.status(404).send({
                message: "Voter Not Found.",
            });

        voterInfo = voterInfo.map((v) => Object.assign({}, v));
        voterInfo[0].app_user_id = Bin2HexUUID(voterInfo[0].app_user_id);
        return res.status(200).send(voterInfo[0]);
    } catch (err) {
        console.log("Caught Error in Fetching Voter Info " + err);
        return res.status(400).send({
            message: "Error in fetching voter info: " + err,
        });
    }
});

app.post("/findmyprecinct", async (req, res) => {
    var firstName = req.body.first_name;
    var middleName = req.body.middle_name;
    var lastName = req.body.last_name;
    var birthDate = req.body.birth_date;
    var selectQry =
        "SELECT ?? FROM ?? WHERE first_name = ? AND middle_name = ? AND last_name = ? and birth_date = ? and isRegistered = true";

    let query = mysql.format(selectQry, [
        "precinct_number",
        "voters",
        firstName,
        middleName,
        lastName,
        birthDate,
    ]);

    try {
        var rows = await db_query(query, dbPool);

        if (rows == "")
            return res.status(404).send({ message: "No precinct assigned." });

        selectQry = "SELECT * FROM  ??  WHERE precinct_number = ?";
        query = mysql.format(selectQry, ["precinct", rows[0].precinct_number]);
        try {
            var precinctData = await db_query(query, dbPool);
            if (precinctData == "")
                return res.status(400).send({
                    message: "No Precinct Data Found",
                });
            return res.status(200).send({
                precinct_data: precinctData[0],
            });
        } catch (err) {
            return res.status(400).send({
                message: "Failed to Fetch Precinct Data. " + err,
            });
        }
    } catch (err) {
        return res.status(400).send({
            message: "Failed to Fetch Precinct Data. " + err,
        });
    }
});

app.post("/confirmvoter", async (req, res) => {
    var id = req.body.id;
    var precinctNumber = req.body.precinct_number;
    var voterIdNumber = req.body.voter_id_number;

    var updateQry =
        "UPDATE voters SET ?? = ?, ?? = ? , ?? = ?, ?? = ? where app_user_id = UUID_TO_BIN(?)";
    let query = mysql.format(updateQry, [
        "isRegistered",
        true,
        "voter_id_number",
        voterIdNumber,
        "precinct_number",
        precinctNumber,
        "registration_status",
        "ACTIVE",
        id,
    ]);
    try {
        await db_query(query, dbPool);
        return res.status(200).send();
    } catch (err) {
        console.log(err);
        return res.status(400).send();
    }
});
app.get("/appusers", async (req, res) => {
    let query = mysql.format("SELECT * FROM ??", ["app_users"]);
    try {
        var rows = await db_query(query, dbPool);
        rows = rows.map((v) => Object.assign({}, v));
        await rows.forEach((row) => {
            row.id = Bin2HexUUID(row.id);
        });

        return res.status(200).send(rows);
    } catch (err) {
        return res.status(400).send(err);
    }
});

app.get("/voters", async (req, res) => {
    let query = mysql.format("SELECT * FROM ??", ["voters"]);
    try {
        var rows = await db_query(query, dbPool);
        rows = rows.map((v) => Object.assign({}, v));
        await rows.forEach((row) => {
            row.app_user_id = Bin2HexUUID(row.app_user_id);
        });
        return res.status(200).send(rows);
    } catch (err) {
        return res.status(400).send(err);
    }
});

app.get("/precincts", async (req, res) => {
    let query = mysql.format("SELECT * FROM ??", ["precinct"]);
    try {
        var rows = await db_query(query, dbPool);
        rows = rows.map((v) => Object.assign({}, v));
        return res.status(200).send(rows);
    } catch (err) {
        return res.status(400).send(err);
    }
});
async function getUserByEmail(email, table) {
    var selectQry = "SELECT * FROM ?? WHERE ?? = ?";

    let query = mysql.format(selectQry, [table, "email", email]);
    try {
        var results = await db_query(query, dbPool);
        return results;
    } catch (err) {
        console.log(`Caught Error while querying database: ${err}`);
    }
}

async function getUserById(id, table) {
    var selectQry = "SELECT * FROM ?? WHERE BIN_TO_UUID(??) = ?";

    let query = mysql.format(selectQry, [table, "app_user_id", id]);
    try {
        var results = await db_query(query, dbPool);
        return results;
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

function Bin2HexUUID(bin) {
    var hex = new Buffer(bin, "base64").toString("hex");
    return hex.replace(/^(.{8})(.{4})(.{4})(.{4})(.{12})$/, function () {
        return (
            arguments[1] +
            "-" +
            arguments[2] +
            "-" +
            arguments[3] +
            "-" +
            arguments[4] +
            "-" +
            arguments[5]
        );
    });
}

app.listen(5000, () => {
    console.log("Server running on PORT 5000");
});
