package com.challenge.domain.services;

import com.challenge.infrastructure.services.VoterStatusClient;
import com.challenge.presentation.dtos.VoterStatusResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoterStatusService {

    private final VoterStatusClient voterStatusClient;

    @Autowired
    public VoterStatusService(VoterStatusClient voterStatusClient) {
        this.voterStatusClient = voterStatusClient;
    }

    public String checkVoterStatus(String cpf) {
        try {
            VoterStatusResponseDto response = voterStatusClient.getVoterStatus(cpf);
            return response.getStatus();
        } catch (Exception e) {
            return "Invalid CPF or error when checking voting status";
        }
    }
}
