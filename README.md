# MoviesApp - Part 1 & 2
Sample mobile app for Android using TheMovieDB API for Android ND

Activities:
  * Main Activity.java - containinig a RecyclerView that displays the movie posters 
      filtering by most popular or top rated movies
  * MovieDetailActivity.java - containinig the details (image, title, release date, vote avarage and synopsis)
      from the selected image in the previous activity
      
 Usage:
  * In order to be able to run the app first create an account and get an API key from https://www.themoviedb.org/documentation/api
  * Then create a 'gradle.properties' file in the root folder and set the API key to a variable called 'TMDB_ApiKey'
      

App Screenshots:


![Alt text](/../app-screenshots/movies_app_screenshots/1.png?raw=false "MainActivity")

![Alt text](/../app-screenshots/movies_app_screenshots/2.png?raw=false "MovieDetailView")

![Alt text](/../app-screenshots/movies_app_screenshots/3.png?raw=false "Trailers & Reviews")

![Alt text](/../app-screenshots/movies_app_screenshots/4.png?raw=false "Favorites View")


TMDB API used: https://developers.themoviedb.org/4/getting-started
