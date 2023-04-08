# Garage-App
This is an Android application that allows users to create a list of vehicles they own or wish to purchase. The application provides login/signup support and uses SQLite for storing the database locally.

# Features
The Garage App provides the following features:

* User authentication: Users can create a new account or log in to an existing one.
* Car list: Users can create a list of Car they own or wish to purchase. Each Car can have a make name, model name, model id, make id and car image.
* Car details: Users can view the details of a vehicle.
* Add/edit/delete vehicles: Users can add a new vehicle, edit the car by adding car image, or delete a car from the list.
* Local database: The application uses SQLite for storing the database locally on the device.

# Technologies
The following technologies were used to build the Garage App:

* Java: The programming language used to develop the Android application.
* Android Studio: The IDE used to develop the Android application.
* SQLite: The database management system used to store the data locally on the device.

## Note:
    I choose SQL becasue SQLite is a popular choice for storing structured data locally on an Android device because it is lightweight, efficient, and provides a flexible SQL-based interface for organizing and querying data. It is optimized for small to medium-sized databases and can handle complex queries and transactions.

Room is an abstraction layer built on top of SQLite that provides a more convenient and type-safe way to work with databases in Android. It provides compile-time verification of SQL queries, a simplified data access API, and support for LiveData and RxJava for reactive programming. Room is suitable for larger, more complex databases or applications where compile-time safety is preferred.

Shared Preferences, on the other hand, are used to store small amounts of data in key-value pairs, typically for storing user preferences or application settings. Shared Preferences are not suitable for storing large amounts of structured data.
