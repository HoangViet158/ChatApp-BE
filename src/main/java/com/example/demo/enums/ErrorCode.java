package com.example.demo.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(1001, "Unauthorized", HttpStatus.UNAUTHORIZED),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_NOT_MATCH(1005, "Old password not match", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1006, "User not existed or wrong password ", HttpStatus.NOT_FOUND),
    GMAIL_INVALID(1007, "Gmail is invalid", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(1008, "Invalid refresh token", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED(1009, "Refresh token expired", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1010, "Email already exists", HttpStatus.BAD_REQUEST),
    CONVERSATION_NOT_EXISTED(2001, "Conversation not existed", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(3001, "Role not found", HttpStatus.NOT_FOUND),

    CONVERSATION_MEMBER_NOT_EXISTED(4001, "Member not existed", HttpStatus.NOT_FOUND),

    MESSAGE_NOT_FOUND(5001, "Message not existed", HttpStatus.NOT_FOUND),

    MESSAGE_STATUS_NOT_FOUND(6001, "Message status not existed", HttpStatus.NOT_FOUND),

    ATTACHMENT_NOT_FOUND(7001, "Attachment not exist", HttpStatus.NOT_FOUND),
    DELETE_ATTACHMENT_FAILED(7002, "delete attachment failed", HttpStatus.BAD_REQUEST),

    FILE_UPLOAD_FAILED( 8001, "File upload failed", HttpStatus.BAD_REQUEST),

    NOTIFICATION_NOT_FOUND(9001, "Notification not exist", HttpStatus.NOT_FOUND)
    
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
