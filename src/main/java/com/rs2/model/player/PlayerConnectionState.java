package com.rs2.model.player;

public enum PlayerConnectionState {
    HANDSHAKE,
    JS5,
    LOGIN_PAYLOAD,
    LOGIN_QUEUED,
    IN_GAME,
    DISCONNECTING,
    DISCONNECTED;

}

