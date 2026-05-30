CREATE TABLE messages (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    conversation_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,

    content TEXT,

    message_type ENUM(
        'TEXT',
        'IMAGE',
        'VIDEO',
        'FILE',
        'VOICE',
        'SYSTEM'
    ) DEFAULT 'TEXT',

    reply_to_message_id BIGINT,

    is_edited BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_messages_conversation
        FOREIGN KEY(conversation_id)
        REFERENCES conversations(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_messages_sender
        FOREIGN KEY(sender_id)
        REFERENCES users(id),

    CONSTRAINT fk_messages_reply
        FOREIGN KEY(reply_to_message_id)
        REFERENCES messages(id)
);

CREATE INDEX idx_messages_conversation_created
ON messages(conversation_id, created_at DESC);