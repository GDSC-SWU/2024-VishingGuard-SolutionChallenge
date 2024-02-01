//package com.gdsc_solutionchallenge.backend.domain.info.controller;
//
//import com.gdsc_solutionchallenge.backend.domain.info.domain.Prevention;
//import com.gdsc_solutionchallenge.backend.domain.route.domain.Route;
//import com.gdsc_solutionchallenge.backend.domain.route.service.RouteService;
//import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
//import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
//import com.gdsc_solutionchallenge.backend.global.error.BaseException;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/route")
//@Tag(name = "사전 예방 & 신고 절차 & 신고처 API", description = "사전 예방 & 신고 절차 & 신고처 API 모음")
//public class PreventionController {
//    private final RouteService routeService;
//
//    @GetMapping("/prevention")
//    @Operation(summary = "사전 예방", description = "사전 예방 API")
//    public ResponseEntity<Object> loadPrevention(){
//        try {
//            List<Prevention> loadPrevention = routeService.loadRoute();
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(new BaseResponse<>(HttpStatus.OK.value(), "홈 화면 로딩 완료", routes));
//        } catch (BaseException e) {
//            return ResponseEntity
//                    .status(e.getCode())
//                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"+e.getMessage()));
//        }
//    }
//
//    @GetMapping("/procedure")
//    @Operation(summary = "신고 절차", description = "신고 절차 API")
//    public ResponseEntity<Object> loadPrevention(){
//        try {
//            List<Prevention> loadPrevention = routeService.loadRoute();
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(new BaseResponse<>(HttpStatus.OK.value(), "홈 화면 로딩 완료", routes));
//        } catch (BaseException e) {
//            return ResponseEntity
//                    .status(e.getCode())
//                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"+e.getMessage()));
//        }
//    }
//
//    @GetMapping("/place")
//    @Operation(summary = "신고처", description = "신고처 API")
//    public ResponseEntity<Object> loadPrevention(){
//        try {
//            List<Prevention> loadPrevention = routeService.loadRoute();
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(new BaseResponse<>(HttpStatus.OK.value(), "홈 화면 로딩 완료", routes));
//        } catch (BaseException e) {
//            return ResponseEntity
//                    .status(e.getCode())
//                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"+e.getMessage()));
//        }
//    }
//}
