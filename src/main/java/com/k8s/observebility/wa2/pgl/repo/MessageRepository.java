package com.k8s.observebility.wa2.pgl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.k8s.observebility.wa2.pgl.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
