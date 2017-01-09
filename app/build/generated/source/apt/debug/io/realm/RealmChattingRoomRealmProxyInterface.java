package io.realm;


public interface RealmChattingRoomRealmProxyInterface {
    public long realmGet$matchingId();
    public void realmSet$matchingId(long value);
    public com.blink.blink.Realm.RealmUser realmGet$matchedUser();
    public void realmSet$matchedUser(com.blink.blink.Realm.RealmUser value);
    public RealmList<com.blink.blink.Realm.RealmMessage> realmGet$messages();
    public void realmSet$messages(RealmList<com.blink.blink.Realm.RealmMessage> value);
}
