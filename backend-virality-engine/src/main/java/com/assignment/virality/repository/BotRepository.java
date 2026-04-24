package com.assignment.virality.repository;

import com.assignment.virality.entity.Bot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotRepository extends JpaRepository<Bot, Long> {
}