package com.example.springbootchatting.stomp.dto;

import com.example.springbootchatting.stomp.domain.Message;
import com.example.springbootchatting.stomp.type.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private MessageType type; // 메시지 타입
    private Long roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time; // 채팅 발송 시간

    /* 파일 업로드 관련 변수 */
    private String s3DataUrl; // 파일 업로드 url
    private String fileName; // 파일이름
    private String fileDir; // s3 파일 경로

    public Message toEntity() {
        return Message.builder()
            .messageType(type)
            .sender(sender)
            .message(message)
//            .time(time)
            .s3DataUrl(this.s3DataUrl)
            .fileName(this.fileName)
            .fileDir(this.fileDir)
            .build();
    }
}
