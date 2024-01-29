package com.gdsc_solutionchallenge.backend.domain.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // 새로운 메서드 추가
    Optional<String> findPasswordByEmail(String email);
}