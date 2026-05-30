package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.ConversationMember;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMember, Long> {
    public List<ConversationMember> findByConversationId(Long id);
}
