package com.pentalign.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invitations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id","receiver_id","game_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false, length = 20)
    private String status;  // e.g. PENDING, ACCEPTED, DECLINED

    @Column(nullable = false, name = "sent_at")
    private LocalDateTime sentAt;
}