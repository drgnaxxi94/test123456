package com.example.test123456.repository;

import com.example.test123456.entity.Like;
import com.example.test123456.entity.Member;
import com.example.test123456.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeInfoRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.member = :member AND l.notification = :notification")
    Optional<Like> findMemberAndNotification(@Param("member") Member member, @Param("notification") Notification notification);

}
