package com.mycompany.instagramcredentialvalidation;

public class profileProperties {
    private final long id;
    private final boolean result;

    public profileProperties(long id, boolean result) {
        this.id = id;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public boolean getContent() {
        return result;
    }
}
