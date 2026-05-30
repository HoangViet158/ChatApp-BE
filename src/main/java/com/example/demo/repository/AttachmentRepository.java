package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Attachment;
import com.example.demo.entity.Message;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByMessage(Message message);
}
