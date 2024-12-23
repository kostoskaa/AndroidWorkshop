The Carpooling Android Application facilitates efficient and user-friendly carpooling for drivers and passengers. Drivers can post available rides, while passengers can browse, choose, and review the rides.

Application Flow:
New users can register and provide their role (Driver/Passenger). Returning users can log in with their credentials.
If you're a driver, you can add rides and ride details. You can also review your passengers (passengers that have chosen your ride). 
If you're a passenger, you can view all available rides. You can click "Details" to see the driver and ride details (car type/registration). You can select "Choose Ride" to book the ride. You can also review your driver.

How to Run:
Clone the repository to your local machine. Open the project in Android Studio. Sync Gradle and build the project. Run the app on an emulator or a physical Android device.

Technical Details:
SQLite is used to store user data, ride details, and ratings.
Dynamic RecyclerViews are used to display the available rides to passengers, and the passengers to the drivers.
Fragments are used for portrait/landscape displaying.
