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
	"middle_name": "michael",
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
	"id": "12345678-1234-1234-1234-123456789123",
	"first_name": "john",
	"middle_name": "michael",
	"last_name": "doe",
	"email": "jdoe@apper.ph"
}

404 NOT FOUND
```

**Register Voter**
```
POST /register
{
	"id": "12345678-1234-1234-1234-123456789123",
	"first_name": "john",
	"middle_name": "michael",
	"last_name": "doe",
	"birth_date": "1995-06-25",
	"birth_province": "metro manila",
	"birth_city": "muntinlupa",
	"civil_status": "SINGLE",
	"sex": "MALE",
	"street": "narra st",
	"subdivision": "ayala alabang villaga",
	"barangay": "ayala alabang",
	"city": "muntinlupa",
	"province": "metro manila",
	"years_in_city": "25",
	"years_in_ph": "25"
}

201 CREATED
```

**Find My Precinct**
```
POST /findmyprecinct
{
	"first_name": "john",
	"middle_name": "michael",
	"last_name": "doe",
	"birth_date": "1995-06-25",
}

200 OK
404 NOT FOUND
```

**Get Candidates**
```
GET /candidates/{position}

200 OK
[
	{
		"first_name": "Rodrigo",
		"image": ""
	}
]
```

**Get Candidate**
```
GET /candidates/profile/{id}

200 OK
{
	"name": "Rodrigo Roa Duterte",
	"position": "President",
	"party": "PDP-Laban"
}
```


**Get Voter**
```
GET /voter/12345678-1234-1234-1234-123456789123

200 OK
{
	"id": "12345678-1234-1234-1234-123456789123",
	"first_name": "john",
	"middle_name": "michael",
	"last_name": "doe",
	"birth_date": "1995-06-25",
	"birth_province": "metro manila",
	"birth_city": "mmuntinlupa",
	"civil_status": "SINGLE",
	"sex": "MALE",
	"street": "narra st",
	"subdivision": "ayala alabang village",
	"barangay": "ayala alabang",
	"city": "muntinlupa",
	"province": "metro manila",
	"years_in_city": "25",
	"years_in_ph": "25",
	"registration_status": "PENDING"
	"precinct_number": null,
	"voter_id_number": null,
	"date_registered": "2021-05-13"
}

404 NOT FOUND
```

**Approve Voter Registration**
```
POST /confirmvoter
{
	"id": "12345678-1234-1234-1234-123456789012",
	"precinct_number": "1234A",
	"voter_id_number": "1234-5678912-3"
}

200 OK
```


**Get All Voters**
```
GET /voters

200 OK
[
	{
		"id": "12345678-1234-1234-1234-123456789123",
		"first_name": "john",
		"middle_name": "michael",
		"last_name": "doe",
		"birth_date": "1995-06-25",
		"birth_province": "metro manila",
		"birth_city": "muntinlupa",
		"civil_status": "SINGLE",
		"sex": "MALE",
		"street": "narra st",
		"subdivision": "ayala alabang village",
		"barangay": "ayala alabang",
		"city": "muntinlupa",
		"province": "metro manila",
		"years_in_city": "25",
		"years_in_ph": "25",
		"registration_status": "PENDING"
		"precinct_number": null,
		"voter_id_number": null,
		"date_registered": "2021-05-13"
	}
]
```

**Get All Precincts**
```
GET /precincts

200 OK
[
	{
		"precinct_number": "1234A",
		"barangay": "Ayala Alabang",
		"city": "Muntinlupa",
		"province": "Metro Manila",
		"polling_place": "De La Salle Santiago Zobel School"
	}
]
```


