package com.yoda.accountProject.member.repository;

import com.yoda.accountProject.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderAndUserId(String provider, String userId);

}
