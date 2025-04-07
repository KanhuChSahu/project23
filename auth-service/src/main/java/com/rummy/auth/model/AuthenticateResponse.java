package com.rummy.auth.model;

public class AuthenticateResponse {
    private boolean error;
    private int code;
    private String message;
    private AuthenticateData data;

    public AuthenticateResponse(boolean error, int code, String message, AuthenticateData data) {
        this.error = error;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthenticateData getData() {
        return data;
    }

    public void setData(AuthenticateData data) {
        this.data = data;
    }

    public static class AuthenticateData {
        private String sessionId;
        private String authToken;

        public AuthenticateData(String sessionId, String authToken) {
            this.sessionId = sessionId;
            this.authToken = authToken;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }
    }
}