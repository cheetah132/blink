package io.realm;


public interface LocalDBRealmProxyInterface {
    public com.blink.blink.Realm.RealmUser realmGet$user();
    public void realmSet$user(com.blink.blink.Realm.RealmUser value);
    public RealmList<com.blink.blink.Realm.RealmUser> realmGet$matchedUsers();
    public void realmSet$matchedUsers(RealmList<com.blink.blink.Realm.RealmUser> value);
    public RealmList<com.blink.blink.Realm.RealmChattingRoom> realmGet$chattingRooms();
    public void realmSet$chattingRooms(RealmList<com.blink.blink.Realm.RealmChattingRoom> value);
}
