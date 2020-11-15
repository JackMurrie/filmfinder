SELECT id FROM movie JOIN
    (SELECT movie_id from director where name="Quentin Tarantino" LIMIT 10) as movies
ON movie.id=movies.movie_id
ORDER BY movie.rating; 

SELECT id FROM movie JOIN
    (SELECT movie_id from movie_genre where genre_name="Romance") as movies
ON movie.id=movies.movie_id
ORDER BY movie.rating DESC
LIMIT 10; 

SELECT id FROM movie JOIN
(SELECT movie_id FROM director WHERE name="Quentin Tarantino") AS movies
ON movie.id=movies.movie_id
ORDER BY movie.rating DESC
LIMIT 10;