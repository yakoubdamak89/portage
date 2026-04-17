package com.portage.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ExpenseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_report_id", nullable = false)
    private ExpenseReport expenseReport;

    @Column(nullable = false)
    private LocalDate date;

    private String category;
    private String description;

    @Builder.Default
    private BigDecimal amount = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal vatAmount = BigDecimal.ZERO;

    private String receiptPath;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
