# Vot API Documentation
## Features

- Profile
- Voter's Registration
- Know Your Candidates
- Find My Precinct

**Create Account**
```
POST /signup
{
	"first_name": "john",
	"middle_name": "",
	"last_name": "doe",
	"email": "jdoe@apper.ph"
	"password": "password"
}

201 CREATED
401 UNAUTHORIZED
```

**Login**
```
POST /login
{
	"email": "jdoe@apper.ph"
	"password": "password"
}

200 OK
{
	"id": "",
	"first_name": "john",
	"middle_name": "",
	"last_name": "",
	"email": ""
}

404 NOT FOUND
```

**Get Voter**
```
GET /voter/{id}

200 OK
{
	"id": "",
	"first_name": "john",
	"middle_name": "",
	"last_name": "",
	"birth_date": "yyyy-MM-DD",
	"birth_province": "",
	"birth_city": "",
	"civil_status": "SINGLE",
	"sex": "MALE",
	"street": "",
	"subdivision": "",
	"barangay": "",
	"city": "",
	"province": "",
	"years_in_city": "",
	"years_in_ph": "",
	"precinct_number": "",
	"voter_id_number": "",
	"date_registered": "yyyy-MM-DD"
}

404 NOT FOUND
```

**Register Voter**
```
POST /register
{
	"id": "",
	"first_name": "john",
	"middle_name": "",
	"last_name": "",
	"birth_date": "yyyy-MM-DD",
	"birth_province": "",
	"birth_city": "",
	"civil_status": "SINGLE",
	"sex": "MALE",
	"street": "",
	"subdivision": "",
	"barangay": "",
	"city": "",
	"province": "",
	"years_in_city": "",
	"years_in_ph": ""
}

201 CREATED
```
