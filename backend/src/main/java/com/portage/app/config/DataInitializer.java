package com.portage.app.config;

import com.portage.app.domain.entity.*;
import com.portage.app.domain.enums.*;
import com.portage.app.repository.*;
import com.portage.app.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final ConsultantRepository consultantRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final MissionRepository missionRepository;
    private final TimesheetRepository timesheetRepository;
    private final InvoiceRepository invoiceRepository;
    private final WalletService walletService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (userRepository.count() > 0) {
                log.info("Data already initialized, skipping.");
                return;
            }

            log.info("Initializing demo data...");

            // Admin user
            User admin = userRepository.save(User.builder()
                    .firstName("Admin")
                    .lastName("Portage")
                    .email("admin@portage.fr")
                    .password(passwordEncoder.encode("Admin123!"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build());

            // Gestionnaire user
            User gestionnaire = userRepository.save(User.builder()
                    .firstName("Marie")
                    .lastName("Dupont")
                    .email("gestionnaire@portage.fr")
                    .password(passwordEncoder.encode("Gestionnaire123!"))
                    .role(Role.GESTIONNAIRE)
                    .enabled(true)
                    .build());

            // Consultant 1 user
            User userConsultant1 = userRepository.save(User.builder()
                    .firstName("Jean")
                    .lastName("Martin")
                    .email("consultant1@portage.fr")
                    .password(passwordEncoder.encode("Consultant123!"))
                    .role(Role.SALARIE_PORTE)
                    .enabled(true)
                    .build());

            // Consultant 2 user
            User userConsultant2 = userRepository.save(User.builder()
                    .firstName("Sophie")
                    .lastName("Leroy")
                    .email("consultant2@portage.fr")
                    .password(passwordEncoder.encode("Consultant123!"))
                    .role(Role.SALARIE_PORTE)
                    .enabled(true)
                    .build());

            // Consultants
            Consultant consultant1 = consultantRepository.save(Consultant.builder()
                    .user(userConsultant1)
                    .firstName("Jean")
                    .lastName("Martin")
                    .email("consultant1@portage.fr")
                    .phone("0612345678")
                    .address("12 Rue de la Paix, Paris 75001")
                    .dateOfBirth(LocalDate.of(1985, 3, 15))
                    .contractType(ContractType.PORTAGE)
                    .dailyRate(new BigDecimal("600.00"))
                    .hourlyRate(new BigDecimal("75.00"))
                    .managementFeeRate(new BigDecimal("10.00"))
                    .status("ACTIVE")
                    .manager(gestionnaire)
                    .build());

            Consultant consultant2 = consultantRepository.save(Consultant.builder()
                    .user(userConsultant2)
                    .firstName("Sophie")
                    .lastName("Leroy")
                    .email("consultant2@portage.fr")
                    .phone("0698765432")
                    .address("45 Avenue des Champs, Lyon 69001")
                    .dateOfBirth(LocalDate.of(1990, 7, 22))
                    .contractType(ContractType.PORTAGE)
                    .dailyRate(new BigDecimal("700.00"))
                    .hourlyRate(new BigDecimal("87.50"))
                    .managementFeeRate(new BigDecimal("10.00"))
                    .status("ACTIVE")
                    .manager(gestionnaire)
                    .build());

            // Client companies
            ClientCompany client1 = clientCompanyRepository.save(ClientCompany.builder()
                    .name("TechCorp SA")
                    .siret("12345678901234")
                    .address("5 Rue Innovation, Paris 75008")
                    .billingAddress("5 Rue Innovation, Paris 75008")
                    .city("Paris")
                    .postalCode("75008")
                    .country("France")
                    .contactName("Pierre Lambert")
                    .contactEmail("p.lambert@techcorp.fr")
                    .contactPhone("0145678901")
                    .paymentTermsDays(30)
                    .active(true)
                    .vatNumber("FR12345678901")
                    .build());

            ClientCompany client2 = clientCompanyRepository.save(ClientCompany.builder()
                    .name("Digital Solutions SARL")
                    .siret("98765432109876")
                    .address("20 Boulevard Tech, Lyon 69002")
                    .billingAddress("20 Boulevard Tech, Lyon 69002")
                    .city("Lyon")
                    .postalCode("69002")
                    .country("France")
                    .contactName("Isabelle Moreau")
                    .contactEmail("i.moreau@digitalsolutions.fr")
                    .contactPhone("0478901234")
                    .paymentTermsDays(45)
                    .active(true)
                    .vatNumber("FR98765432109")
                    .build());

            // Missions
            Mission mission1 = missionRepository.save(Mission.builder()
                    .title("Développement Application Web")
                    .description("Mission de développement d'une application web React/Spring Boot")
                    .consultant(consultant1)
                    .clientCompany(client1)
                    .startDate(LocalDate.now().minusMonths(2))
                    .endDate(LocalDate.now().plusMonths(4))
                    .dailyRate(new BigDecimal("600.00"))
                    .estimatedDays(new BigDecimal("120"))
                    .status(MissionStatus.ACTIVE)
                    .billingType("DAILY")
                    .build());

            Mission mission2 = missionRepository.save(Mission.builder()
                    .title("Architecture Microservices")
                    .description("Mission d'architecture et mise en place microservices")
                    .consultant(consultant2)
                    .clientCompany(client2)
                    .startDate(LocalDate.now().minusMonths(1))
                    .endDate(LocalDate.now().plusMonths(5))
                    .dailyRate(new BigDecimal("700.00"))
                    .estimatedDays(new BigDecimal("150"))
                    .status(MissionStatus.ACTIVE)
                    .billingType("DAILY")
                    .build());

            // Timesheets
            int currentMonth = LocalDate.now().getMonthValue();
            int currentYear = LocalDate.now().getYear();

            Timesheet timesheet1 = timesheetRepository.save(Timesheet.builder()
                    .consultant(consultant1)
                    .mission(mission1)
                    .month(currentMonth)
                    .year(currentYear)
                    .status(TimesheetStatus.VALIDATED)
                    .totalDays(new BigDecimal("20"))
                    .totalHours(new BigDecimal("160"))
                    .submittedAt(java.time.LocalDateTime.now().minusDays(5))
                    .validatedAt(java.time.LocalDateTime.now().minusDays(2))
                    .validatedBy(gestionnaire)
                    .build());

            Timesheet timesheet2 = timesheetRepository.save(Timesheet.builder()
                    .consultant(consultant2)
                    .mission(mission2)
                    .month(currentMonth)
                    .year(currentYear)
                    .status(TimesheetStatus.SUBMITTED)
                    .totalDays(new BigDecimal("18"))
                    .totalHours(new BigDecimal("144"))
                    .submittedAt(java.time.LocalDateTime.now().minusDays(3))
                    .build());

            // Invoices
            invoiceRepository.save(Invoice.builder()
                    .invoiceNumber("INV-" + currentYear + String.format("%02d", currentMonth) + "-0001")
                    .consultant(consultant1)
                    .clientCompany(client1)
                    .mission(mission1)
                    .timesheet(timesheet1)
                    .issueDate(LocalDate.now().minusDays(10))
                    .dueDate(LocalDate.now().plusDays(20))
                    .totalHT(new BigDecimal("12000.00"))
                    .vatRate(new BigDecimal("20.00"))
                    .totalTTC(new BigDecimal("14400.00"))
                    .status(InvoiceStatus.SENT)
                    .build());

            invoiceRepository.save(Invoice.builder()
                    .invoiceNumber("INV-" + currentYear + String.format("%02d", currentMonth) + "-0002")
                    .consultant(consultant2)
                    .clientCompany(client2)
                    .mission(mission2)
                    .timesheet(timesheet2)
                    .issueDate(LocalDate.now().minusDays(5))
                    .dueDate(LocalDate.now().plusDays(40))
                    .totalHT(new BigDecimal("12600.00"))
                    .vatRate(new BigDecimal("20.00"))
                    .totalTTC(new BigDecimal("15120.00"))
                    .status(InvoiceStatus.DRAFT)
                    .build());

            // Wallets
            walletService.addMovement(consultant1.getId(), WalletMovementType.CREDIT,
                    new BigDecimal("10800.00"), "Salaire net - " + currentMonth + "/" + currentYear,
                    null, "PAYSLIP");

            walletService.addMovement(consultant2.getId(), WalletMovementType.CREDIT,
                    new BigDecimal("11340.00"), "Salaire net - " + currentMonth + "/" + currentYear,
                    null, "PAYSLIP");

            log.info("Demo data initialized successfully!");
            log.info("Users created: admin@portage.fr, gestionnaire@portage.fr, consultant1@portage.fr, consultant2@portage.fr");
        };
    }
}
