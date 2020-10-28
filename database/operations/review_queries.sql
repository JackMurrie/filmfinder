REPLACE INTO review(movie_id, user_id, review, rating) values 
	(2, 282, "", 5),
    (3, 184, "", 4.5),
    (3, 216, "", 5),
    (2, 232, "", 5),
    (2, 249, "", 4),
    (2, 269, "Ok", 4);

SELECT * from user;

SELECT * from review;

-- Get common reviewed movies
Select distinct r1.movie_id from review r1, review r2 where r1.user_id=184 and r2.user_id=216 and r1.movie_id = r2.movie_id;

SELECT avg(rating) from review where movie_id=3;

alter table movie
add column avg_rating decimal(10,2) NOT NULL DEFAULT 0;