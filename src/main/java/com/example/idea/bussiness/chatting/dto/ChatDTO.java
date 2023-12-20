package com.example.idea.bussiness.chatting.dto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 생성
public class ChatDTO {

    public enum MessageType{
        ENTER,TALK, EXIST
    }

    private MessageType messageType; // 메시지 타입
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    private String time; // 채팅 발송 시간
}
