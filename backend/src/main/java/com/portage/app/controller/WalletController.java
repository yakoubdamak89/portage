package com.portage.app.controller;

import com.portage.app.dto.response.WalletMovementResponse;
import com.portage.app.dto.response.WalletResponse;
import com.portage.app.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
@Tag(name = "Wallet", description = "Wallet management APIs")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{consultantId}")
    @Operation(summary = "Get wallet for consultant")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable Long consultantId) {
        return ResponseEntity.ok(walletService.getWallet(consultantId));
    }

    @GetMapping("/{consultantId}/movements")
    @Operation(summary = "Get wallet movement history")
    public ResponseEntity<List<WalletMovementResponse>> getHistory(@PathVariable Long consultantId) {
        return ResponseEntity.ok(walletService.getHistory(consultantId));
    }
}
