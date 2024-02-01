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

    public List<Route> loadRoute() throws Exception {
        // post 레포에서 게시글의 title 전부 빼와서  Dto List 생성
        List<Route> routes = routeRepository.getAll();

        return routes;
    }
}
