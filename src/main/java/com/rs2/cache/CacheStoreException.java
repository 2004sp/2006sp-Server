package com.rs2.cache;

public final class CacheStoreException
extends Exception {
    public CacheStoreException(String text2) {
        super(text2);
    }

    public CacheStoreException(Exception exception) {
        super(exception);
    }
}

