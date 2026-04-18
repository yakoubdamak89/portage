package com.portage.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id", nullable = false)
    private Consultant consultant;

    @Column(nullable = false)
    private String name;

    private String type;
    private String filePath;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    private String category;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
