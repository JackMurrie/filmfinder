USE film_finder;

-- Follower View
CREATE VIEW follower_view AS
SELECT DISTINCT u1.name user_name, u1.id user_id, u2.name follows_name, u2.id2 follows_id
FROM 
    (SELECT name, follower_id id FROM user u
        INNER JOIN follower f ON u.id = f.follower_id) u1,
    (SELECT name, follower_id id, followed_id id2 FROM user u
        INNER JOIN follower f ON u.id = f.followed_id) u2
WHERE u1.id = u2.id;
