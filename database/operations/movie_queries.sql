SELECT * FROM movie_genre;
SELECT * FROM director where movie_id=2;

-- INSERT INTO movie_genre (genre_name, movie_id)
-- VALUES
-- ("horror", 2);

-- INSERT INTO director (name, movie_id)
-- VALUES
-- ("Fred", 2);

DELETE FROM director WHERE movie_id=2;
-- DELETE FROM movie_genre;


SELECT m.id, m.name movie, d.name director FROM movie m
    LEFT JOIN director d on m.id=d.movie_id
    WHERE d.name is NULL;

SELECT m.id, m.name movie, g.genre_name FROM movie m
    LEFT JOIN movie_genre g on m.id=g.movie_id
    WHERE g.genre_name is NULL;

SELECT COUNT(*) FROM movie;