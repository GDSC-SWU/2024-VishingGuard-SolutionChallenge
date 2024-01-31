package com.gdsc_solutionchallenge.backend.domain.route.service;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.home.dto.HomeResDto;
import com.gdsc_solutionchallenge.backend.domain.route.domain.Route;
import com.gdsc_solutionchallenge.backend.domain.route.domain.RouteRepository;
import com.gdsc_solutionchallenge.backend.domain.route.dto.RouteResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
