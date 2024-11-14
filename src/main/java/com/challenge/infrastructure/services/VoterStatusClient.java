package com.challenge.infrastructure.services;

import com.challenge.presentation.dtos.VoterStatusResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "voterStatusClient", url = "https://user-info.herokuapp.com")
public interface VoterStatusClient {

    @GetMapping("/users/{cpf}")
    VoterStatusResponseDto getVoterStatus(@PathVariable("cpf") String voterId);
}
