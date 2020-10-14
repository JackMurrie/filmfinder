USE film_finder;

-- Get followers for user id number
SELECT f.user_name FROM follower_view f WHERE f.follows_id=1;

-- Get people user follows for user id number
SELECT f.follows_name FROM follower_view f WHERE f.user_id=3;

SELECT d.name Director, m.id `Movie ID` FROM movie m INNER JOIN director d on m.id = d.movie_id;

SELECT * FROM movie_genre;
SELECT * FROM genre;

CALL insert_movie_id(1, 'Drama', 'Colin Frith');
CALL insert_movie_id(1, 'Comedy', 'Colin Frith');
CALL insert_movie_id(1, 'Action', 'Johnny Blake');
