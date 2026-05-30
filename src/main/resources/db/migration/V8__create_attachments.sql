CREATE TABLE attachments (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    message_id BIGINT NOT NULL,

    file_url TEXT NOT NULL,

    file_type VARCHAR(50),

    file_size BIGINT,

    public_id VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_attachments_message
        FOREIGN KEY(message_id)
        REFERENCES messages(id)
        ON DELETE CASCADE
);