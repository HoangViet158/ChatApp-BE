CREATE TABLE message_status (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    message_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    status ENUM('SENT', 'DELIVERED', 'SEEN') NOT NULL,

    seen_at TIMESTAMP NULL,

    CONSTRAINT fk_message_status_message
        FOREIGN KEY(message_id)
        REFERENCES messages(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_message_status_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);