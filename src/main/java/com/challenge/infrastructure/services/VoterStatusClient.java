package com.challenge.infrastructure.services;

import com.challenge.presentation.dtos.VoterStatusResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "voterStatusClient", url = "https://user-info.herokuapp.com/users/")
public interface VoterStatusClient {

    @GetMapping("/status")
    VoterStatusResponseDto getVoterStatus(@RequestParam("id") String voterId);
}
