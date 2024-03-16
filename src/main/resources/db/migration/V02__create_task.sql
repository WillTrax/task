CREATE TABLE IF NOT EXISTS `task`
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('DONE', 'PENDING') DEFAULT 'PENDING',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
