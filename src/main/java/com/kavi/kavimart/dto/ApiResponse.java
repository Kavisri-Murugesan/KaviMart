package com.kavi.kavimart.dto;

/** Standard JSON response envelope. */
public record ApiResponse<T>(boolean success, String message, T data) {}