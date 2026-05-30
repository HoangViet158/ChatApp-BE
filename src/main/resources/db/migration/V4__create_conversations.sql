CREATE TABLE conversations (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(255),

    type ENUM('PRIVATE', 'GROUP') NOT NULL,

    avatar_url TEXT,

    created_by BIGINT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_conversations_created_by
        FOREIGN KEY(created_by)
        REFERENCES users(id)
);