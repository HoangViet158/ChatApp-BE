package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(
    name = "messages",
    indexes = {
        @Index(
            name = "idx_messages_conversation_created",
            columnList = "conversation_id, created_at"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // Conversation
    // =========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(
        name = "conversation_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_messages_conversation")
    )
    private Conversation conversation;

    // =========================
    // Sender
    // =========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(
        name = "sender_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_messages_sender")
    )
    private User sender;

    // =========================
    // Message content
    // =========================
    @Column(columnDefinition = "TEXT")
    private String content;

    // =========================
    // Message type
    // =========================
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType = MessageType.TEXT;

    // =========================
    // Reply message
    // =========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(
        name = "reply_to_message_id",
        foreignKey = @ForeignKey(name = "fk_messages_reply")
    )
    private Message replyToMessage;

    // =========================
    // Flags
    // =========================
    @Builder.Default
    @Column(name = "is_edited")
    private Boolean isEdited = false;

    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

        @OneToMany(
        mappedBy = "message",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

}