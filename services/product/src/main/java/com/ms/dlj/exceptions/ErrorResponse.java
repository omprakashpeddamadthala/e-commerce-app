package com.ms.dlj.exceptions;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
