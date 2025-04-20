package com.pentalign.backend.repository;

import com.pentalign.backend.entities.GameMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMoveRepository extends JpaRepository<GameMove, Long> {
}
