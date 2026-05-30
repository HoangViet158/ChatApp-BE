package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {

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
        foreignKey = @ForeignKey(name = "fk_attachments_message")
    )
    private Message message;

    // =========================
    // File URL
    // =========================
    @Column(name = "file_url", nullable = false, columnDefinition = "TEXT")
    private String fileUrl;

    // =========================
    // File Type
    // =========================
    @Column(name = "file_type", length = 50)
    private String fileType;

    // =========================
    // File Size (bytes)
    // =========================
    @Column(name = "file_size")
    private Long fileSize;

    // =========================
    // Cloudinary / S3 public id
    // =========================
    @Column(name = "public_id")
    private String publicId;

    // =========================
    // Created At
    // =========================
    @Column(
        name = "created_at",
        updatable = false
    )
    private LocalDateTime createdAt;

    // =========================
    // Auto timestamp
    // =========================
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}