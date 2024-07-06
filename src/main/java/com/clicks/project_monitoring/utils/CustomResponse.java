package com.clicks.project_monitoring.utils;

public record CustomResponse(
        boolean success,
        Object data
) { }
