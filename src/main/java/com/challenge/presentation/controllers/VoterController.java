package com.challenge.presentation.controllers;

import com.challenge.domain.services.VoterStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoterController {

    private final VoterStatusService voterStatusService;

    @Autowired
    public VoterController(VoterStatusService voterStatusService) {
        this.voterStatusService = voterStatusService;
    }

    @GetMapping("/voter-status/{cpf}")
    public String getVoterStatus(@PathVariable String cpf) {
        return voterStatusService.checkVoterStatus(cpf);
    }
}
