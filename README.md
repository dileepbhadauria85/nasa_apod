# Nasa Astronomy Picture of the Day everyday.
# Description
Each day a different image or photograph of our fascinating universe is featured.
Data is fetched from API and saved in DB for offline viewing, data is updated on new date change every day.
# Notes  
* Used MVVM architecture with kotlin.
* Used Retrofit library to fetch the apod image and stored in Room DB for offline access.
# Improvement Areas
* We can use dependency injection framework such as Dagger2 to separate the dependencies in the app.
* We can also separate the DB and Rest Api as different modules in the app
* The Api response has date of different time zone, 
* APOD images fetching from DB on the basis of local date (DB Query Improvement)
* Image Caching can be improved although GLide is there 
* Implimentation of Unit Test case (on Mock Data)
