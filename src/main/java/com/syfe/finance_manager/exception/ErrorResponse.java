package com.syfe.finance_manager.exception;

import java.time.Instant;

public record ErrorResponse(int status, String error, String message, Instant timestamp) { }
