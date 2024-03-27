-- Chèn dữ liệu vào bảng users
INSERT INTO users (id, username, password, first_name, last_name, role, gender, phone_number, date_of_birth, mail, enable)
VALUES
    (UUID(), 'admin', 'admin123', 'John', 'Doe', 'user', TRUE, '123456789', '1990-01-01', 'john@example.com', TRUE),
    (UUID(), 'jane_doe', 'password456', 'Jane', 'Doe', 'admin', FALSE, '987654321', '1992-03-15', 'jane@example.com', TRUE);

-- Chèn dữ liệu vào bảng post
INSERT INTO post (id, title, body, status, total_like, total_comment, created_by)
VALUES
    (UUID(), 'First Post', 'This is the body of the first post.', 'published', 0, 0, (SELECT id FROM users WHERE username = 'admin')),
    (UUID(), 'Second Post', 'This is the body of the second post.', 'published', 0, 0, (SELECT id FROM users WHERE username = 'jane_doe'));

-- -- Chèn dữ liệu vào bảng medias
 INSERT INTO medias (id, base_name, public_url, post_id)
 VALUES
     (UUID(), 'image1.jpg', 'http://example.com/image1.jpg', (SELECT id FROM post ORDER BY RANDOM() LIMIT 1)),
     (UUID(), 'image2.jpg', 'http://example.com/image2.jpg', (SELECT id FROM post ORDER BY RANDOM() LIMIT 1));

 -- Chèn dữ liệu vào bảng favorites
 INSERT INTO favorites (id, created_at, user_id)
 VALUES
     (UUID(), CURRENT_TIMESTAMP(), (SELECT id FROM users LIMIT 1)),
     (UUID(), CURRENT_TIMESTAMP(), (SELECT id FROM users LIMIT 2));

 -- Chèn dữ liệu vào bảng reactions
 INSERT INTO reactions (id, object_type, opject_id, type, create_by)
 VALUES
     (UUID(), 'post', (SELECT id FROM post LIMIT 1), 1, (SELECT id FROM users LIMIT 1)),
     (UUID(), 'post', (SELECT id FROM post LIMIT 2), 2, (SELECT id FROM users LIMIT 2));
