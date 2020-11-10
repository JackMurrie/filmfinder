SELECT * FROM movie m 
WHERE  m.id NOT IN (
    SELECT w.movie_id FROM watched w WHERE w.user_id=3
)
ORDER BY m.rating LIMIT 10;

-- Get users not in blacklist
SELECT u.id FROM user u WHERE u.id NOT IN (SELECT b.blacklisted_id FROM blacklist b WHERE b.owner_id=5);

-- Get average for each reviewed movie
SELECT IFNULL(AVG(rev.rating),0) r, rev.movie_id FROM review rev GROUP BY rev.movie_id ORDER BY r DESC;

-- Get reviews by non balcklisted users
SELECT IFNULL(AVG(r.rating),0) avg_rating, COUNT(r.rating) numReviews, movie_id FROM review r WHERE r.user_id NOT IN (
    SELECT u.id FROM user u WHERE u.id IN (SELECT b.blacklisted_id FROM blacklist b WHERE b.owner_id=5)
)
GROUP BY r.movie_id ORDER BY avg_rating DESC;

-- Get reviews by non balcklisted users and movies not in watched list
SELECT IFNULL(AVG(r.rating),0) avg_rating, COUNT(r.rating) numReviews, r.movie_id FROM review r WHERE r.user_id NOT IN (
    SELECT u.id FROM user u WHERE u.id IN (SELECT b.blacklisted_id FROM blacklist b WHERE b.owner_id=5)
) AND r.movie_id NOT IN (SELECT w.movie_id FROM watched w WHERE w.user_id=5)
GROUP BY r.movie_id ORDER BY avg_rating DESC;

-- Apply Shrinkage estimator to get reviews by non blacklisted and not watched movies: v = 2
-- SELECT C total_avg, R avg_rating, n numReviews, movie_id, (n/(n+m)*R+m/(n+m)*C) ranking
SELECT movie_id, (n/(n+m)*R+m/(n+m)*C) ranking
FROM
(SELECT AVG(rd1.avg_rating) C, AVG(n)+1 m FROM (
    SELECT IFNULL(AVG(r.rating),0) avg_rating, COUNT(r.rating) n, r.movie_id FROM review r WHERE r.user_id NOT IN (
        SELECT u.id FROM user u WHERE u.id IN (SELECT b.blacklisted_id FROM blacklist b WHERE b.owner_id=5)
    ) AND r.movie_id NOT IN (SELECT w.movie_id FROM watched w WHERE w.user_id=5)
    GROUP BY r.movie_id
) rd1) a_t
JOIN (
    SELECT IFNULL(AVG(r.rating),0) R, COUNT(r.rating) n, r.movie_id FROM review r WHERE r.user_id NOT IN (
        SELECT u.id FROM user u WHERE u.id IN (SELECT b.blacklisted_id FROM blacklist b WHERE b.owner_id=5)
    ) AND r.movie_id NOT IN (SELECT w.movie_id FROM watched w WHERE w.user_id=5)
    GROUP BY r.movie_id
) rd2
ORDER BY ranking DESC;

SELECT * FROM blacklist;

SELECT * FROM watched;

-- INSERT INTO blacklist (owner_id, blacklisted_id) VALUES (5, 1), (5,2);

SELECT * FROM user;
SELECT * FROM review;
