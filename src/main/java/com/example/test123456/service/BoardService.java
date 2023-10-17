package com.example.test123456.service;

import com.example.test123456.dto.NotificationFormDto;
import com.example.test123456.entity.Like;
import com.example.test123456.entity.Member;
import com.example.test123456.entity.Notification;
import com.example.test123456.repository.LikeInfoRepository;
import com.example.test123456.repository.MemberRepository;
import com.example.test123456.repository.Notice_boardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    @Autowired
    private Notice_boardRepository boardRepository;
    @Autowired
    private LikeInfoRepository likeInfoRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void saveBoard(NotificationFormDto notificationFormDto) {
        Notification notification = notificationFormDto.createNotification();
        Member member = memberRepository.findByEmail(notificationFormDto.getEmail()).get();
        notification.setName(member.getName());
        System.out.println(notification.getName());
        notification.setLike_count(0L);
        notification.setView_count(0L);
        boardRepository.save(notification);
    }

    public NotificationFormDto notificationDetail(Long notificationId) {
        Notification notification = boardRepository.findById(notificationId).orElseThrow((EntityNotFoundException::new));
        notification.viewCount();
        NotificationFormDto notificationFormDto = NotificationFormDto.of(notification);

        return notificationFormDto;
    }

    public void deleteBoard(Long notificationId) {
        boardRepository.deleteById(notificationId);
    }

    public void updateBoard(NotificationFormDto notificationFormDto) {
        Notification notification = boardRepository.findById(notificationFormDto.getNotificationId()).orElseThrow((EntityNotFoundException::new));
        notification.updateEntity(notificationFormDto);
    }

    /* 유저가 좋아요 버튼 눌렀을 때, 엔티티 Like 데이터 생성&삭제 */
    /* 좋아요 갯수 더하고(Notification 엔티티), Like 엔티티 추가 또는 삭제, 반환 값은 좋아요 갯수 */
    /* 받아온 data는 1 또는 -1의 값을 가지고, 공지 게시판의 엔티티에 좋아요 데이터에 data를 더함*/
    public NotificationFormDto like_count(Long data, Long notificationId, String email) {
        Member member = memberRepository.findByEmail(email).get();
        Notification notification = boardRepository.findById(notificationId).orElseThrow((EntityNotFoundException::new));
        Optional<Like> optionalLike =likeInfoRepository.findMemberAndNotification(member, notification);

        notification.likeCount(data);
        NotificationFormDto notificationFormDto = NotificationFormDto.of(notification);
        if(optionalLike.isPresent()) {
            Like like = optionalLike.get();
            likeInfoRepository.delete(like);
            notificationFormDto.setLiked(false);
        } else {
            Like like = new Like();
            like.setLiked(true);
            like.setMember(member);
            like.setNotification(notification);
            likeInfoRepository.save(like);
            notificationFormDto.setLiked(true);
        }

        return notificationFormDto;
    }

    public NotificationFormDto like_count_load(Long notificationId, String email) {
        Member member = memberRepository.findByEmail(email).get();
        Notification notification = boardRepository.findById(notificationId).orElseThrow((EntityNotFoundException::new));
        Optional<Like> optionalLike =likeInfoRepository.findMemberAndNotification(member, notification);
        NotificationFormDto notificationFormDto = NotificationFormDto.of(notification);
        if(optionalLike.isPresent()) {
            Like like = optionalLike.get();
            likeInfoRepository.delete(like);
            notificationFormDto.setLiked(false);
        } else {
            Like like = new Like();
            like.setLiked(true);
            like.setMember(member);
            like.setNotification(notification);
            likeInfoRepository.save(like);
            notificationFormDto.setLiked(true);
        }

        return notificationFormDto;
    }
}
