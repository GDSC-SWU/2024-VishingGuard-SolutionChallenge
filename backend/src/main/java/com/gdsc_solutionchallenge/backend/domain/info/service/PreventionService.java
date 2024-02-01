package com.gdsc_solutionchallenge.backend.domain.info.service;

import com.gdsc_solutionchallenge.backend.domain.info.domain.Prevention;
import com.gdsc_solutionchallenge.backend.domain.info.domain.PreventionRepository;
import com.gdsc_solutionchallenge.backend.domain.route.domain.Route;
import com.gdsc_solutionchallenge.backend.domain.route.domain.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreventionService {
    private final PreventionRepository preventionRepository;

    public List<Prevention> loadPrevention() throws Exception {
        List<Prevention> preventions = preventionRepository.getAll();

        return preventions;
    }
}
