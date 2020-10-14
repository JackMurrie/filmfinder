USE film_finder;

CREATE PROCEDURE insert_movie_id (
    m_id INT,
    g_name VARCHAR(100),
    d_name VARCHAR(100)
)
BEGIN
    INSERT IGNORE INTO genre (name) Values (g_name);
    INSERT IGNORE INTO movie (id) VALUES (m_id);
    INSERT IGNORE INTO movie_genre (movie_id, genre_name) VALUES (m_id, g_name);
    INSERT IGNORE INTO director (movie_id, name) VALUES (m_id, d_name);
END