package com.example.idea.bussiness.chatting.controller;

import com.example.idea.bussiness.chatting.dto.ChatDTO;
import com.example.idea.bussiness.chatting.dto.ChatDTO.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    // 채팅방 입장과 퇴장, 메시지 전송을 엔드 포인트로 구별하는게 좋을 까요?
    // 아니면 메시지 타입으로 구별하는게 좋을 까요?
    @MessageMapping("/chat/room")
    public void message(ChatDTO message) {
        if (MessageType.ENTER.equals(message.getMessageType())) {
            message.setMessage(message.getSender()+"님이 입장하셨습니다.");
        }
        //메시지를 보낼 때, 메시지를 보낸 사람의 이름과 보낸 시간도 같이 띄워주려면 여기서 처리를 해야 할까요?
        // 아니면 클라이언트쪽에서 처리를 해야 좋을까요?
        if (MessageType.TALK.equals(message.getMessageType())) {
            message.setMessage(message.getMessage());
        }
        if (MessageType.EXIST.equals(message.getMessageType())) {
            message.setMessage(message.getSender()+"님이 퇴장하셨습니다.");
        }
        messagingTemplate.convertAndSend("/topic/chat/room", message);
    }

}
