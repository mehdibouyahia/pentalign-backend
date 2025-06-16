package com.pentalign.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_moves",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"game_id", "move_order"}),
                @UniqueConstraint(columnNames = {"game_id", "row", "col"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private User player;

    @Column(name = "move_order", nullable = false)
    private int moveOrder;

    @Column(nullable = false)
    private int row;
    
    @Column(nullable = false)
    private int col;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;
}
