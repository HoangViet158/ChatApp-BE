package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.demo.enums.MessageDeliveryStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(
    name = "message_status",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_message_user",
            columnNames = {
                "message_id",
                "user_id"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // Message
    // =========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(
        name = "message_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_message_status_message")
    )
    private Message message;

    // =========================
    // User
    // =========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_message_status_user")
    )
    private User user;

    // =========================
    // Status
    // =========================
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageDeliveryStatus status;

    // =========================
    // Seen time
    // =========================
    @Column(name = "seen_at")
    private LocalDateTime seenAt;
}