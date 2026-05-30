CREATE TABLE conversation_members (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    conversation_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    role ENUM('MEMBER', 'ADMIN') DEFAULT 'MEMBER',

    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    last_read_message_id BIGINT,

    CONSTRAINT fk_conv_member_conversation
        FOREIGN KEY(conversation_id)
        REFERENCES conversations(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_conv_member_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    UNIQUE(conversation_id, user_id)
);