package com.example.jache.chat.dto;


import lombok.*;


import java.time.LocalDate;


public class ChatDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ChatRoomReqDto{
        private String chatRoomName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ChatRoomResDto{
        private Long chatRoomId;
        private String chatRoomName;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatMessage {

        private Long roomId;
        private String sender;
        private String message;
        private LocalDate sendDate;

    }
}
