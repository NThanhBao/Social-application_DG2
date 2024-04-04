-- Chèn dữ liệu vào bảng users

INSERT INTO users (id, username, password, first_name, last_name, role, gender, phone_number, date_of_birth, mail, enable)
VALUES
    (UUID(), 'admin', 'admin123', 'John', 'Doe', 'admin', TRUE, '123456789', '1990-01-01', 'admin@example.com', TRUE),
    (UUID(), 'user', '$2a$10$k9NoQE3/HhRIGgEDEoqwPuNJct0SXMvk3Qo1FOkWBolsgWm/BBL4O', 'Jane', 'Doe', 'user', FALSE, '987654321', '1992-03-15', 'user@example.com', TRUE);

