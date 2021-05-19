# iBoto API Documentation
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
	"years_in_ph": "25"
	"date_registered": "2021-05-13"
}

201 CREATED
```

**Find My Precinct**
```
POST /find-my-precinct
{
	"first_name": "john",
	"middle_name": "michael",
	"last_name": "doe",
	"birth_date": "1995-06-25",
}

200 OK
400 BAD REQUEST
404 NOT FOUND
```

**Get Candidates**
```
GET /candidates/local/mayor&metro+manila&quezon+city

200 OK
{
	"candidates": [
    		{
			"id": 71,
			"first_name": "Herbert Bautista",
			"position": "Mayor",
			"party": "Liberal",
			"is_national": 0,
			"province": "Metro Manila",
			"municipality": "Quezon City",
            		"district": "",
            		"image": "https://voters-ap-bucket.s3-ap-southeast-1.amazonaws.com/71.jpg?{s3 pre-signed url}"
		}
	]
}


GET /candidates/national/president

200 OK
{
	"candidates": [
    		{
			"id": 1,
			"name": "Jejomar Binay",
			"position": "President",
			"party": "UNA",
			"is_national": 1,
			"province": "",
			"municipality": "",
			"district": "",
			"image": "https://voters-ap-bucket.s3-ap-southeast-1.amazonaws.com/1.jpg?{s3 pre-signed url}"
        	}
	]
}
```



**Get Voter**
```
GET /voter/12345678-1234-1234-1234-123456789123

200 OK
{
	"id": "12345678-1234-1234-1234-123456789123",
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

404 NOT FOUND
```

