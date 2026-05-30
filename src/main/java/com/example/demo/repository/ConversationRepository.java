package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Conversation;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByNameContainingIgnoreCase(String name);
    @Query(value = """
    SELECT c.*
    FROM conversations c
    INNER JOIN conversation_members cm
        ON c.id = cm.conversation_id
    WHERE cm.user_id = :userId
""", nativeQuery = true)

    List<Conversation> getConversationsByUserId(@org.springframework.data.repository.query.Param ("userId") Long userId);
}
