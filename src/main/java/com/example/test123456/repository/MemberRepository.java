package com.example.test123456.repository;

import com.example.test123456.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

   Optional <Member> findByEmail(String email);

}
