package com.portage.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payslips")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id", nullable = false)
    private Consultant consultant;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @Builder.Default
    private BigDecimal grossSalary = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal netSalary = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal employerCharges = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal employeeCharges = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal expenses = BigDecimal.ZERO;

    private String filePath;
    private LocalDateTime generatedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
