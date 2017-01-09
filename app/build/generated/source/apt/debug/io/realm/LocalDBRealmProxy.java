package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocalDBRealmProxy extends com.blink.blink.Realm.LocalDB
    implements RealmObjectProxy, LocalDBRealmProxyInterface {

    static final class LocalDBColumnInfo extends ColumnInfo
        implements Cloneable {

        public long userIndex;
        public long matchedUsersIndex;
        public long chattingRoomsIndex;

        LocalDBColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.userIndex = getValidColumnIndex(path, table, "LocalDB", "user");
            indicesMap.put("user", this.userIndex);
            this.matchedUsersIndex = getValidColumnIndex(path, table, "LocalDB", "matchedUsers");
            indicesMap.put("matchedUsers", this.matchedUsersIndex);
            this.chattingRoomsIndex = getValidColumnIndex(path, table, "LocalDB", "chattingRooms");
            indicesMap.put("chattingRooms", this.chattingRoomsIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final LocalDBColumnInfo otherInfo = (LocalDBColumnInfo) other;
            this.userIndex = otherInfo.userIndex;
            this.matchedUsersIndex = otherInfo.matchedUsersIndex;
            this.chattingRoomsIndex = otherInfo.chattingRoomsIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final LocalDBColumnInfo clone() {
            return (LocalDBColumnInfo) super.clone();
        }

    }
    private LocalDBColumnInfo columnInfo;
    private ProxyState proxyState;
    private RealmList<com.blink.blink.Realm.RealmUser> matchedUsersRealmList;
    private RealmList<com.blink.blink.Realm.RealmChattingRoom> chattingRoomsRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("user");
        fieldNames.add("matchedUsers");
        fieldNames.add("chattingRooms");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    LocalDBRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (LocalDBColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(com.blink.blink.Realm.LocalDB.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    public com.blink.blink.Realm.RealmUser realmGet$user() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.userIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.blink.blink.Realm.RealmUser.class, proxyState.getRow$realm().getLink(columnInfo.userIndex), false, Collections.<String>emptyList());
    }

    public void realmSet$user(com.blink.blink.Realm.RealmUser value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("user")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.userIndex);
                return;
            }
            if (!RealmObject.isValid(value)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy) value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            row.getTable().setLink(columnInfo.userIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.userIndex);
            return;
        }
        if (!(RealmObject.isManaged(value) && RealmObject.isValid(value))) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        proxyState.getRow$realm().setLink(columnInfo.userIndex, ((RealmObjectProxy)value).realmGet$proxyState().getRow$realm().getIndex());
    }

    public RealmList<com.blink.blink.Realm.RealmUser> realmGet$matchedUsers() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (matchedUsersRealmList != null) {
            return matchedUsersRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.matchedUsersIndex);
            matchedUsersRealmList = new RealmList<com.blink.blink.Realm.RealmUser>(com.blink.blink.Realm.RealmUser.class, linkView, proxyState.getRealm$realm());
            return matchedUsersRealmList;
        }
    }

    public void realmSet$matchedUsers(RealmList<com.blink.blink.Realm.RealmUser> value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("matchedUsers")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.blink.blink.Realm.RealmUser> original = value;
                value = new RealmList<com.blink.blink.Realm.RealmUser>();
                for (com.blink.blink.Realm.RealmUser item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.matchedUsersIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmModel linkedObject : (RealmList<? extends RealmModel>) value) {
            if (!(RealmObject.isManaged(linkedObject) && RealmObject.isValid(linkedObject))) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)linkedObject).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            links.add(((RealmObjectProxy)linkedObject).realmGet$proxyState().getRow$realm().getIndex());
        }
    }

    public RealmList<com.blink.blink.Realm.RealmChattingRoom> realmGet$chattingRooms() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (chattingRoomsRealmList != null) {
            return chattingRoomsRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.chattingRoomsIndex);
            chattingRoomsRealmList = new RealmList<com.blink.blink.Realm.RealmChattingRoom>(com.blink.blink.Realm.RealmChattingRoom.class, linkView, proxyState.getRealm$realm());
            return chattingRoomsRealmList;
        }
    }

    public void realmSet$chattingRooms(RealmList<com.blink.blink.Realm.RealmChattingRoom> value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("chattingRooms")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.blink.blink.Realm.RealmChattingRoom> original = value;
                value = new RealmList<com.blink.blink.Realm.RealmChattingRoom>();
                for (com.blink.blink.Realm.RealmChattingRoom item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.chattingRoomsIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmModel linkedObject : (RealmList<? extends RealmModel>) value) {
            if (!(RealmObject.isManaged(linkedObject) && RealmObject.isValid(linkedObject))) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)linkedObject).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            links.add(((RealmObjectProxy)linkedObject).realmGet$proxyState().getRow$realm().getIndex());
        }
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("LocalDB")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("LocalDB");
            if (!realmSchema.contains("RealmUser")) {
                RealmUserRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add(new Property("user", RealmFieldType.OBJECT, realmSchema.get("RealmUser")));
            if (!realmSchema.contains("RealmUser")) {
                RealmUserRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add(new Property("matchedUsers", RealmFieldType.LIST, realmSchema.get("RealmUser")));
            if (!realmSchema.contains("RealmChattingRoom")) {
                RealmChattingRoomRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add(new Property("chattingRooms", RealmFieldType.LIST, realmSchema.get("RealmChattingRoom")));
            return realmObjectSchema;
        }
        return realmSchema.get("LocalDB");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_LocalDB")) {
            Table table = sharedRealm.getTable("class_LocalDB");
            if (!sharedRealm.hasTable("class_RealmUser")) {
                RealmUserRealmProxy.initTable(sharedRealm);
            }
            table.addColumnLink(RealmFieldType.OBJECT, "user", sharedRealm.getTable("class_RealmUser"));
            if (!sharedRealm.hasTable("class_RealmUser")) {
                RealmUserRealmProxy.initTable(sharedRealm);
            }
            table.addColumnLink(RealmFieldType.LIST, "matchedUsers", sharedRealm.getTable("class_RealmUser"));
            if (!sharedRealm.hasTable("class_RealmChattingRoom")) {
                RealmChattingRoomRealmProxy.initTable(sharedRealm);
            }
            table.addColumnLink(RealmFieldType.LIST, "chattingRooms", sharedRealm.getTable("class_RealmChattingRoom"));
            table.setPrimaryKey("");
            return table;
        }
        return sharedRealm.getTable("class_LocalDB");
    }

    public static LocalDBColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_LocalDB")) {
            Table table = sharedRealm.getTable("class_LocalDB");
            final long columnCount = table.getColumnCount();
            if (columnCount != 3) {
                if (columnCount < 3) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 3 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 3 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 3 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final LocalDBColumnInfo columnInfo = new LocalDBColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("user")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'user' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("user") != RealmFieldType.OBJECT) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'RealmUser' for field 'user'");
            }
            if (!sharedRealm.hasTable("class_RealmUser")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_RealmUser' for field 'user'");
            }
            Table table_0 = sharedRealm.getTable("class_RealmUser");
            if (!table.getLinkTarget(columnInfo.userIndex).hasSameSchema(table_0)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'user': '" + table.getLinkTarget(columnInfo.userIndex).getName() + "' expected - was '" + table_0.getName() + "'");
            }
            if (!columnTypes.containsKey("matchedUsers")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'matchedUsers'");
            }
            if (columnTypes.get("matchedUsers") != RealmFieldType.LIST) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'RealmUser' for field 'matchedUsers'");
            }
            if (!sharedRealm.hasTable("class_RealmUser")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_RealmUser' for field 'matchedUsers'");
            }
            Table table_1 = sharedRealm.getTable("class_RealmUser");
            if (!table.getLinkTarget(columnInfo.matchedUsersIndex).hasSameSchema(table_1)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'matchedUsers': '" + table.getLinkTarget(columnInfo.matchedUsersIndex).getName() + "' expected - was '" + table_1.getName() + "'");
            }
            if (!columnTypes.containsKey("chattingRooms")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'chattingRooms'");
            }
            if (columnTypes.get("chattingRooms") != RealmFieldType.LIST) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'RealmChattingRoom' for field 'chattingRooms'");
            }
            if (!sharedRealm.hasTable("class_RealmChattingRoom")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_RealmChattingRoom' for field 'chattingRooms'");
            }
            Table table_2 = sharedRealm.getTable("class_RealmChattingRoom");
            if (!table.getLinkTarget(columnInfo.chattingRoomsIndex).hasSameSchema(table_2)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'chattingRooms': '" + table.getLinkTarget(columnInfo.chattingRoomsIndex).getName() + "' expected - was '" + table_2.getName() + "'");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'LocalDB' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_LocalDB";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.blink.blink.Realm.LocalDB createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(3);
        if (json.has("user")) {
            excludeFields.add("user");
        }
        if (json.has("matchedUsers")) {
            excludeFields.add("matchedUsers");
        }
        if (json.has("chattingRooms")) {
            excludeFields.add("chattingRooms");
        }
        com.blink.blink.Realm.LocalDB obj = realm.createObjectInternal(com.blink.blink.Realm.LocalDB.class, true, excludeFields);
        if (json.has("user")) {
            if (json.isNull("user")) {
                ((LocalDBRealmProxyInterface) obj).realmSet$user(null);
            } else {
                com.blink.blink.Realm.RealmUser userObj = RealmUserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("user"), update);
                ((LocalDBRealmProxyInterface) obj).realmSet$user(userObj);
            }
        }
        if (json.has("matchedUsers")) {
            if (json.isNull("matchedUsers")) {
                ((LocalDBRealmProxyInterface) obj).realmSet$matchedUsers(null);
            } else {
                ((LocalDBRealmProxyInterface) obj).realmGet$matchedUsers().clear();
                JSONArray array = json.getJSONArray("matchedUsers");
                for (int i = 0; i < array.length(); i++) {
                    com.blink.blink.Realm.RealmUser item = RealmUserRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((LocalDBRealmProxyInterface) obj).realmGet$matchedUsers().add(item);
                }
            }
        }
        if (json.has("chattingRooms")) {
            if (json.isNull("chattingRooms")) {
                ((LocalDBRealmProxyInterface) obj).realmSet$chattingRooms(null);
            } else {
                ((LocalDBRealmProxyInterface) obj).realmGet$chattingRooms().clear();
                JSONArray array = json.getJSONArray("chattingRooms");
                for (int i = 0; i < array.length(); i++) {
                    com.blink.blink.Realm.RealmChattingRoom item = RealmChattingRoomRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((LocalDBRealmProxyInterface) obj).realmGet$chattingRooms().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.blink.blink.Realm.LocalDB createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.blink.blink.Realm.LocalDB obj = new com.blink.blink.Realm.LocalDB();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("user")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((LocalDBRealmProxyInterface) obj).realmSet$user(null);
                } else {
                    com.blink.blink.Realm.RealmUser userObj = RealmUserRealmProxy.createUsingJsonStream(realm, reader);
                    ((LocalDBRealmProxyInterface) obj).realmSet$user(userObj);
                }
            } else if (name.equals("matchedUsers")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((LocalDBRealmProxyInterface) obj).realmSet$matchedUsers(null);
                } else {
                    ((LocalDBRealmProxyInterface) obj).realmSet$matchedUsers(new RealmList<com.blink.blink.Realm.RealmUser>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.blink.blink.Realm.RealmUser item = RealmUserRealmProxy.createUsingJsonStream(realm, reader);
                        ((LocalDBRealmProxyInterface) obj).realmGet$matchedUsers().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("chattingRooms")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((LocalDBRealmProxyInterface) obj).realmSet$chattingRooms(null);
                } else {
                    ((LocalDBRealmProxyInterface) obj).realmSet$chattingRooms(new RealmList<com.blink.blink.Realm.RealmChattingRoom>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.blink.blink.Realm.RealmChattingRoom item = RealmChattingRoomRealmProxy.createUsingJsonStream(realm, reader);
                        ((LocalDBRealmProxyInterface) obj).realmGet$chattingRooms().add(item);
                    }
                    reader.endArray();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.blink.blink.Realm.LocalDB copyOrUpdate(Realm realm, com.blink.blink.Realm.LocalDB object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.blink.blink.Realm.LocalDB) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.blink.blink.Realm.LocalDB copy(Realm realm, com.blink.blink.Realm.LocalDB newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.blink.blink.Realm.LocalDB) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.blink.blink.Realm.LocalDB realmObject = realm.createObjectInternal(com.blink.blink.Realm.LocalDB.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);

            com.blink.blink.Realm.RealmUser userObj = ((LocalDBRealmProxyInterface) newObject).realmGet$user();
            if (userObj != null) {
                com.blink.blink.Realm.RealmUser cacheuser = (com.blink.blink.Realm.RealmUser) cache.get(userObj);
                if (cacheuser != null) {
                    ((LocalDBRealmProxyInterface) realmObject).realmSet$user(cacheuser);
                } else {
                    ((LocalDBRealmProxyInterface) realmObject).realmSet$user(RealmUserRealmProxy.copyOrUpdate(realm, userObj, update, cache));
                }
            } else {
                ((LocalDBRealmProxyInterface) realmObject).realmSet$user(null);
            }

            RealmList<com.blink.blink.Realm.RealmUser> matchedUsersList = ((LocalDBRealmProxyInterface) newObject).realmGet$matchedUsers();
            if (matchedUsersList != null) {
                RealmList<com.blink.blink.Realm.RealmUser> matchedUsersRealmList = ((LocalDBRealmProxyInterface) realmObject).realmGet$matchedUsers();
                for (int i = 0; i < matchedUsersList.size(); i++) {
                    com.blink.blink.Realm.RealmUser matchedUsersItem = matchedUsersList.get(i);
                    com.blink.blink.Realm.RealmUser cachematchedUsers = (com.blink.blink.Realm.RealmUser) cache.get(matchedUsersItem);
                    if (cachematchedUsers != null) {
                        matchedUsersRealmList.add(cachematchedUsers);
                    } else {
                        matchedUsersRealmList.add(RealmUserRealmProxy.copyOrUpdate(realm, matchedUsersList.get(i), update, cache));
                    }
                }
            }


            RealmList<com.blink.blink.Realm.RealmChattingRoom> chattingRoomsList = ((LocalDBRealmProxyInterface) newObject).realmGet$chattingRooms();
            if (chattingRoomsList != null) {
                RealmList<com.blink.blink.Realm.RealmChattingRoom> chattingRoomsRealmList = ((LocalDBRealmProxyInterface) realmObject).realmGet$chattingRooms();
                for (int i = 0; i < chattingRoomsList.size(); i++) {
                    com.blink.blink.Realm.RealmChattingRoom chattingRoomsItem = chattingRoomsList.get(i);
                    com.blink.blink.Realm.RealmChattingRoom cachechattingRooms = (com.blink.blink.Realm.RealmChattingRoom) cache.get(chattingRoomsItem);
                    if (cachechattingRooms != null) {
                        chattingRoomsRealmList.add(cachechattingRooms);
                    } else {
                        chattingRoomsRealmList.add(RealmChattingRoomRealmProxy.copyOrUpdate(realm, chattingRoomsList.get(i), update, cache));
                    }
                }
            }

            return realmObject;
        }
    }

    public static long insert(Realm realm, com.blink.blink.Realm.LocalDB object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.blink.blink.Realm.LocalDB.class);
        long tableNativePtr = table.getNativeTablePointer();
        LocalDBColumnInfo columnInfo = (LocalDBColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.LocalDB.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);

        com.blink.blink.Realm.RealmUser userObj = ((LocalDBRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = RealmUserRealmProxy.insert(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        }

        RealmList<com.blink.blink.Realm.RealmUser> matchedUsersList = ((LocalDBRealmProxyInterface) object).realmGet$matchedUsers();
        if (matchedUsersList != null) {
            long matchedUsersNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.matchedUsersIndex, rowIndex);
            for (com.blink.blink.Realm.RealmUser matchedUsersItem : matchedUsersList) {
                Long cacheItemIndexmatchedUsers = cache.get(matchedUsersItem);
                if (cacheItemIndexmatchedUsers == null) {
                    cacheItemIndexmatchedUsers = RealmUserRealmProxy.insert(realm, matchedUsersItem, cache);
                }
                LinkView.nativeAdd(matchedUsersNativeLinkViewPtr, cacheItemIndexmatchedUsers);
            }
            LinkView.nativeClose(matchedUsersNativeLinkViewPtr);
        }


        RealmList<com.blink.blink.Realm.RealmChattingRoom> chattingRoomsList = ((LocalDBRealmProxyInterface) object).realmGet$chattingRooms();
        if (chattingRoomsList != null) {
            long chattingRoomsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.chattingRoomsIndex, rowIndex);
            for (com.blink.blink.Realm.RealmChattingRoom chattingRoomsItem : chattingRoomsList) {
                Long cacheItemIndexchattingRooms = cache.get(chattingRoomsItem);
                if (cacheItemIndexchattingRooms == null) {
                    cacheItemIndexchattingRooms = RealmChattingRoomRealmProxy.insert(realm, chattingRoomsItem, cache);
                }
                LinkView.nativeAdd(chattingRoomsNativeLinkViewPtr, cacheItemIndexchattingRooms);
            }
            LinkView.nativeClose(chattingRoomsNativeLinkViewPtr);
        }

        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.blink.blink.Realm.LocalDB.class);
        long tableNativePtr = table.getNativeTablePointer();
        LocalDBColumnInfo columnInfo = (LocalDBColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.LocalDB.class);
        com.blink.blink.Realm.LocalDB object = null;
        while (objects.hasNext()) {
            object = (com.blink.blink.Realm.LocalDB) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);

                com.blink.blink.Realm.RealmUser userObj = ((LocalDBRealmProxyInterface) object).realmGet$user();
                if (userObj != null) {
                    Long cacheuser = cache.get(userObj);
                    if (cacheuser == null) {
                        cacheuser = RealmUserRealmProxy.insert(realm, userObj, cache);
                    }
                    table.setLink(columnInfo.userIndex, rowIndex, cacheuser, false);
                }

                RealmList<com.blink.blink.Realm.RealmUser> matchedUsersList = ((LocalDBRealmProxyInterface) object).realmGet$matchedUsers();
                if (matchedUsersList != null) {
                    long matchedUsersNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.matchedUsersIndex, rowIndex);
                    for (com.blink.blink.Realm.RealmUser matchedUsersItem : matchedUsersList) {
                        Long cacheItemIndexmatchedUsers = cache.get(matchedUsersItem);
                        if (cacheItemIndexmatchedUsers == null) {
                            cacheItemIndexmatchedUsers = RealmUserRealmProxy.insert(realm, matchedUsersItem, cache);
                        }
                        LinkView.nativeAdd(matchedUsersNativeLinkViewPtr, cacheItemIndexmatchedUsers);
                    }
                    LinkView.nativeClose(matchedUsersNativeLinkViewPtr);
                }


                RealmList<com.blink.blink.Realm.RealmChattingRoom> chattingRoomsList = ((LocalDBRealmProxyInterface) object).realmGet$chattingRooms();
                if (chattingRoomsList != null) {
                    long chattingRoomsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.chattingRoomsIndex, rowIndex);
                    for (com.blink.blink.Realm.RealmChattingRoom chattingRoomsItem : chattingRoomsList) {
                        Long cacheItemIndexchattingRooms = cache.get(chattingRoomsItem);
                        if (cacheItemIndexchattingRooms == null) {
                            cacheItemIndexchattingRooms = RealmChattingRoomRealmProxy.insert(realm, chattingRoomsItem, cache);
                        }
                        LinkView.nativeAdd(chattingRoomsNativeLinkViewPtr, cacheItemIndexchattingRooms);
                    }
                    LinkView.nativeClose(chattingRoomsNativeLinkViewPtr);
                }

            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.blink.blink.Realm.LocalDB object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.blink.blink.Realm.LocalDB.class);
        long tableNativePtr = table.getNativeTablePointer();
        LocalDBColumnInfo columnInfo = (LocalDBColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.LocalDB.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);

        com.blink.blink.Realm.RealmUser userObj = ((LocalDBRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = RealmUserRealmProxy.insertOrUpdate(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
        }

        long matchedUsersNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.matchedUsersIndex, rowIndex);
        LinkView.nativeClear(matchedUsersNativeLinkViewPtr);
        RealmList<com.blink.blink.Realm.RealmUser> matchedUsersList = ((LocalDBRealmProxyInterface) object).realmGet$matchedUsers();
        if (matchedUsersList != null) {
            for (com.blink.blink.Realm.RealmUser matchedUsersItem : matchedUsersList) {
                Long cacheItemIndexmatchedUsers = cache.get(matchedUsersItem);
                if (cacheItemIndexmatchedUsers == null) {
                    cacheItemIndexmatchedUsers = RealmUserRealmProxy.insertOrUpdate(realm, matchedUsersItem, cache);
                }
                LinkView.nativeAdd(matchedUsersNativeLinkViewPtr, cacheItemIndexmatchedUsers);
            }
        }
        LinkView.nativeClose(matchedUsersNativeLinkViewPtr);


        long chattingRoomsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.chattingRoomsIndex, rowIndex);
        LinkView.nativeClear(chattingRoomsNativeLinkViewPtr);
        RealmList<com.blink.blink.Realm.RealmChattingRoom> chattingRoomsList = ((LocalDBRealmProxyInterface) object).realmGet$chattingRooms();
        if (chattingRoomsList != null) {
            for (com.blink.blink.Realm.RealmChattingRoom chattingRoomsItem : chattingRoomsList) {
                Long cacheItemIndexchattingRooms = cache.get(chattingRoomsItem);
                if (cacheItemIndexchattingRooms == null) {
                    cacheItemIndexchattingRooms = RealmChattingRoomRealmProxy.insertOrUpdate(realm, chattingRoomsItem, cache);
                }
                LinkView.nativeAdd(chattingRoomsNativeLinkViewPtr, cacheItemIndexchattingRooms);
            }
        }
        LinkView.nativeClose(chattingRoomsNativeLinkViewPtr);

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.blink.blink.Realm.LocalDB.class);
        long tableNativePtr = table.getNativeTablePointer();
        LocalDBColumnInfo columnInfo = (LocalDBColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.LocalDB.class);
        com.blink.blink.Realm.LocalDB object = null;
        while (objects.hasNext()) {
            object = (com.blink.blink.Realm.LocalDB) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);

                com.blink.blink.Realm.RealmUser userObj = ((LocalDBRealmProxyInterface) object).realmGet$user();
                if (userObj != null) {
                    Long cacheuser = cache.get(userObj);
                    if (cacheuser == null) {
                        cacheuser = RealmUserRealmProxy.insertOrUpdate(realm, userObj, cache);
                    }
                    Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
                } else {
                    Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
                }

                long matchedUsersNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.matchedUsersIndex, rowIndex);
                LinkView.nativeClear(matchedUsersNativeLinkViewPtr);
                RealmList<com.blink.blink.Realm.RealmUser> matchedUsersList = ((LocalDBRealmProxyInterface) object).realmGet$matchedUsers();
                if (matchedUsersList != null) {
                    for (com.blink.blink.Realm.RealmUser matchedUsersItem : matchedUsersList) {
                        Long cacheItemIndexmatchedUsers = cache.get(matchedUsersItem);
                        if (cacheItemIndexmatchedUsers == null) {
                            cacheItemIndexmatchedUsers = RealmUserRealmProxy.insertOrUpdate(realm, matchedUsersItem, cache);
                        }
                        LinkView.nativeAdd(matchedUsersNativeLinkViewPtr, cacheItemIndexmatchedUsers);
                    }
                }
                LinkView.nativeClose(matchedUsersNativeLinkViewPtr);


                long chattingRoomsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.chattingRoomsIndex, rowIndex);
                LinkView.nativeClear(chattingRoomsNativeLinkViewPtr);
                RealmList<com.blink.blink.Realm.RealmChattingRoom> chattingRoomsList = ((LocalDBRealmProxyInterface) object).realmGet$chattingRooms();
                if (chattingRoomsList != null) {
                    for (com.blink.blink.Realm.RealmChattingRoom chattingRoomsItem : chattingRoomsList) {
                        Long cacheItemIndexchattingRooms = cache.get(chattingRoomsItem);
                        if (cacheItemIndexchattingRooms == null) {
                            cacheItemIndexchattingRooms = RealmChattingRoomRealmProxy.insertOrUpdate(realm, chattingRoomsItem, cache);
                        }
                        LinkView.nativeAdd(chattingRoomsNativeLinkViewPtr, cacheItemIndexchattingRooms);
                    }
                }
                LinkView.nativeClose(chattingRoomsNativeLinkViewPtr);

            }
        }
    }

    public static com.blink.blink.Realm.LocalDB createDetachedCopy(com.blink.blink.Realm.LocalDB realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.blink.blink.Realm.LocalDB unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.blink.blink.Realm.LocalDB)cachedObject.object;
            } else {
                unmanagedObject = (com.blink.blink.Realm.LocalDB)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.blink.blink.Realm.LocalDB();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }

        // Deep copy of user
        ((LocalDBRealmProxyInterface) unmanagedObject).realmSet$user(RealmUserRealmProxy.createDetachedCopy(((LocalDBRealmProxyInterface) realmObject).realmGet$user(), currentDepth + 1, maxDepth, cache));

        // Deep copy of matchedUsers
        if (currentDepth == maxDepth) {
            ((LocalDBRealmProxyInterface) unmanagedObject).realmSet$matchedUsers(null);
        } else {
            RealmList<com.blink.blink.Realm.RealmUser> managedmatchedUsersList = ((LocalDBRealmProxyInterface) realmObject).realmGet$matchedUsers();
            RealmList<com.blink.blink.Realm.RealmUser> unmanagedmatchedUsersList = new RealmList<com.blink.blink.Realm.RealmUser>();
            ((LocalDBRealmProxyInterface) unmanagedObject).realmSet$matchedUsers(unmanagedmatchedUsersList);
            int nextDepth = currentDepth + 1;
            int size = managedmatchedUsersList.size();
            for (int i = 0; i < size; i++) {
                com.blink.blink.Realm.RealmUser item = RealmUserRealmProxy.createDetachedCopy(managedmatchedUsersList.get(i), nextDepth, maxDepth, cache);
                unmanagedmatchedUsersList.add(item);
            }
        }

        // Deep copy of chattingRooms
        if (currentDepth == maxDepth) {
            ((LocalDBRealmProxyInterface) unmanagedObject).realmSet$chattingRooms(null);
        } else {
            RealmList<com.blink.blink.Realm.RealmChattingRoom> managedchattingRoomsList = ((LocalDBRealmProxyInterface) realmObject).realmGet$chattingRooms();
            RealmList<com.blink.blink.Realm.RealmChattingRoom> unmanagedchattingRoomsList = new RealmList<com.blink.blink.Realm.RealmChattingRoom>();
            ((LocalDBRealmProxyInterface) unmanagedObject).realmSet$chattingRooms(unmanagedchattingRoomsList);
            int nextDepth = currentDepth + 1;
            int size = managedchattingRoomsList.size();
            for (int i = 0; i < size; i++) {
                com.blink.blink.Realm.RealmChattingRoom item = RealmChattingRoomRealmProxy.createDetachedCopy(managedchattingRoomsList.get(i), nextDepth, maxDepth, cache);
                unmanagedchattingRoomsList.add(item);
            }
        }
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("LocalDB = [");
        stringBuilder.append("{user:");
        stringBuilder.append(realmGet$user() != null ? "RealmUser" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{matchedUsers:");
        stringBuilder.append("RealmList<RealmUser>[").append(realmGet$matchedUsers().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{chattingRooms:");
        stringBuilder.append("RealmList<RealmChattingRoom>[").append(realmGet$chattingRooms().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalDBRealmProxy aLocalDB = (LocalDBRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aLocalDB.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aLocalDB.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aLocalDB.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
