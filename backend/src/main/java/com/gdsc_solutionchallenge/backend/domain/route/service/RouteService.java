package com.gdsc_solutionchallenge.backend.domain.route.service;

import com.gdsc_solutionchallenge.backend.domain.route.domain.Route;
import com.gdsc_solutionchallenge.backend.domain.route.domain.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;

    /**
     * Retrieve the list of routes (locations).
     *
     * @return List of Route entities representing routes.
     * @throws Exception if an error occurs during the route retrieval process.
     */
    public List<Route> loadRoute() throws Exception {
        List<Route> routes = routeRepository.getAll();

        return routes;
    }
}
