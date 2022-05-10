### Real Estate App

### Pre-requisites

* Java 17
* Maven
* an SQL database of your choice (MySQL, Oracle, PostgreSQL etc)
* an IDE of your choice (IntellijIDEA, Eclipse etc)

### Requirements

You must implement the back end system of an application used for real estate management. The application will store the data in an SQL database of your choice.

The application needs to provide implementation for the following use cases:

1. Add a real estate – allows a user to add a new real estate in the database. The following business rules are defined for this use case:
* The real estates are stored in a table in the database
* The following real estate types are supported: STUDIO, TWO_ROOMS, THREE_ROOMS
* If the real estate type is a STUDIO the monthly rate cannot be more than 350 EUR.
* If the real estate type is a TWO_ROOMS the monthly rate cannot be more than 450 EUR.
* If the real estate type is a THREE_ROOMS the monthly rate cannot be less than 455 EUR.

* The following table contains the list of attributes of a real estate location, along with the validations that need to be implemented for each attribute:
 
 Attribute name | Validation
------------- | -----------
 id           | integer positive value, primary key, generated automatically in the database
 owner_name   | text, with maximum length of 200 characters
 monthly_rate | real positive value
 type         | can have only one of these values: STUDIO, TWO_ROOMS, THREE_ROOMS
 parking      | boolean flag that a real estate has parking included. (default is false)

2. Update the price of a real estate - allows a user to update the price of a real estate stored in the database. The user must specify the id of the real estate they want to update. 

3. Retrieve the list of all real estates - allows the user to retrieve the list of all real estates details from the database. The real estates should be sorted by price.

4. Retrieve the list of all real estate types and the number of real estates for each genre from the database. 
eg: <br>
	STUDIO : 1,<br>
	TWO_ROOMS : 4,<br>
	THREE_ROOMS : 0<br>

5. Delete a real estate - The user should be able delete a real estate from the database.  The following business rules are defined for this use case:
* The user must specify the id of the real estate to delete, and that real estate should be removed from the database. 

6. Add parking to a real estate - The user should be able to add parking to a real estate. The following business rules are defined for this use case:
* The user must specify the id of the real estate to add Parking to. The real estate must be marked in the database as parking = true, and the monthly rate should be increased by 30 EUR (while staying in the first use case limits, if adding 30 goes over the limits, no increase should be made to the monthly rate).
* Only real estate properties not marked as parking = true should be viable to this usecase (do not increase rent to a property that already has parking).


Unit tests should be implemented, with a coverage of at least 70% per line.

### Notes
The application may be implemented as any of these options:
* a console application, using only Java SE
* a REST API exposing endpoints for each use case, using Spring / Spring Boot

