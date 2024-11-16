package com.challenge.domain.services;

import com.challenge.infrastructure.services.VoterStatusClient;
import com.challenge.presentation.dtos.VoterStatusResponseDto;
import org.springframework.stereotype.Service;

@Service
public class VoterStatusService {

    private final VoterStatusClient voterStatusClient;

    public VoterStatusService(VoterStatusClient voterStatusClient) {
        this.voterStatusClient = voterStatusClient;
    }

    public String checkVoterStatus(String cpf){
        return voterStatusClient.getVoterStatus(cpf).getStatus();
    }
}
