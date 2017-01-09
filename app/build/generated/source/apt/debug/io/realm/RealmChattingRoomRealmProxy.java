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

public class RealmChattingRoomRealmProxy extends com.blink.blink.Realm.RealmChattingRoom
    implements RealmObjectProxy, RealmChattingRoomRealmProxyInterface {

    static final class RealmChattingRoomColumnInfo extends ColumnInfo
        implements Cloneable {

        public long matchingIdIndex;
        public long matchedUserIndex;
        public long messagesIndex;

        RealmChattingRoomColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.matchingIdIndex = getValidColumnIndex(path, table, "RealmChattingRoom", "matchingId");
            indicesMap.put("matchingId", this.matchingIdIndex);
            this.matchedUserIndex = getValidColumnIndex(path, table, "RealmChattingRoom", "matchedUser");
            indicesMap.put("matchedUser", this.matchedUserIndex);
            this.messagesIndex = getValidColumnIndex(path, table, "RealmChattingRoom", "messages");
            indicesMap.put("messages", this.messagesIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final RealmChattingRoomColumnInfo otherInfo = (RealmChattingRoomColumnInfo) other;
            this.matchingIdIndex = otherInfo.matchingIdIndex;
            this.matchedUserIndex = otherInfo.matchedUserIndex;
            this.messagesIndex = otherInfo.messagesIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final RealmChattingRoomColumnInfo clone() {
            return (RealmChattingRoomColumnInfo) super.clone();
        }

    }
    private RealmChattingRoomColumnInfo columnInfo;
    private ProxyState proxyState;
    private RealmList<com.blink.blink.Realm.RealmMessage> messagesRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("matchingId");
        fieldNames.add("matchedUser");
        fieldNames.add("messages");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmChattingRoomRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmChattingRoomColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(com.blink.blink.Realm.RealmChattingRoom.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public long realmGet$matchingId() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.matchingIdIndex);
    }

    public void realmSet$matchingId(long value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.matchingIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.matchingIdIndex, value);
    }

    public com.blink.blink.Realm.RealmUser realmGet$matchedUser() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.matchedUserIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.blink.blink.Realm.RealmUser.class, proxyState.getRow$realm().getLink(columnInfo.matchedUserIndex), false, Collections.<String>emptyList());
    }

    public void realmSet$matchedUser(com.blink.blink.Realm.RealmUser value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("matchedUser")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.matchedUserIndex);
                return;
            }
            if (!RealmObject.isValid(value)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy) value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            row.getTable().setLink(columnInfo.matchedUserIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.matchedUserIndex);
            return;
        }
        if (!(RealmObject.isManaged(value) && RealmObject.isValid(value))) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        proxyState.getRow$realm().setLink(columnInfo.matchedUserIndex, ((RealmObjectProxy)value).realmGet$proxyState().getRow$realm().getIndex());
    }

    public RealmList<com.blink.blink.Realm.RealmMessage> realmGet$messages() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (messagesRealmList != null) {
            return messagesRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.messagesIndex);
            messagesRealmList = new RealmList<com.blink.blink.Realm.RealmMessage>(com.blink.blink.Realm.RealmMessage.class, linkView, proxyState.getRealm$realm());
            return messagesRealmList;
        }
    }

    public void realmSet$messages(RealmList<com.blink.blink.Realm.RealmMessage> value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("messages")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.blink.blink.Realm.RealmMessage> original = value;
                value = new RealmList<com.blink.blink.Realm.RealmMessage>();
                for (com.blink.blink.Realm.RealmMessage item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.messagesIndex);
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
        if (!realmSchema.contains("RealmChattingRoom")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("RealmChattingRoom");
            realmObjectSchema.add(new Property("matchingId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            if (!realmSchema.contains("RealmUser")) {
                RealmUserRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add(new Property("matchedUser", RealmFieldType.OBJECT, realmSchema.get("RealmUser")));
            if (!realmSchema.contains("RealmMessage")) {
                RealmMessageRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add(new Property("messages", RealmFieldType.LIST, realmSchema.get("RealmMessage")));
            return realmObjectSchema;
        }
        return realmSchema.get("RealmChattingRoom");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_RealmChattingRoom")) {
            Table table = sharedRealm.getTable("class_RealmChattingRoom");
            table.addColumn(RealmFieldType.INTEGER, "matchingId", Table.NOT_NULLABLE);
            if (!sharedRealm.hasTable("class_RealmUser")) {
                RealmUserRealmProxy.initTable(sharedRealm);
            }
            table.addColumnLink(RealmFieldType.OBJECT, "matchedUser", sharedRealm.getTable("class_RealmUser"));
            if (!sharedRealm.hasTable("class_RealmMessage")) {
                RealmMessageRealmProxy.initTable(sharedRealm);
            }
            table.addColumnLink(RealmFieldType.LIST, "messages", sharedRealm.getTable("class_RealmMessage"));
            table.setPrimaryKey("");
            return table;
        }
        return sharedRealm.getTable("class_RealmChattingRoom");
    }

    public static RealmChattingRoomColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_RealmChattingRoom")) {
            Table table = sharedRealm.getTable("class_RealmChattingRoom");
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

            final RealmChattingRoomColumnInfo columnInfo = new RealmChattingRoomColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("matchingId")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'matchingId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("matchingId") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'matchingId' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.matchingIdIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'matchingId' does support null values in the existing Realm file. Use corresponding boxed type for field 'matchingId' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("matchedUser")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'matchedUser' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("matchedUser") != RealmFieldType.OBJECT) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'RealmUser' for field 'matchedUser'");
            }
            if (!sharedRealm.hasTable("class_RealmUser")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_RealmUser' for field 'matchedUser'");
            }
            Table table_1 = sharedRealm.getTable("class_RealmUser");
            if (!table.getLinkTarget(columnInfo.matchedUserIndex).hasSameSchema(table_1)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'matchedUser': '" + table.getLinkTarget(columnInfo.matchedUserIndex).getName() + "' expected - was '" + table_1.getName() + "'");
            }
            if (!columnTypes.containsKey("messages")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'messages'");
            }
            if (columnTypes.get("messages") != RealmFieldType.LIST) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'RealmMessage' for field 'messages'");
            }
            if (!sharedRealm.hasTable("class_RealmMessage")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_RealmMessage' for field 'messages'");
            }
            Table table_2 = sharedRealm.getTable("class_RealmMessage");
            if (!table.getLinkTarget(columnInfo.messagesIndex).hasSameSchema(table_2)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'messages': '" + table.getLinkTarget(columnInfo.messagesIndex).getName() + "' expected - was '" + table_2.getName() + "'");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'RealmChattingRoom' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmChattingRoom";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.blink.blink.Realm.RealmChattingRoom createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(2);
        if (json.has("matchedUser")) {
            excludeFields.add("matchedUser");
        }
        if (json.has("messages")) {
            excludeFields.add("messages");
        }
        com.blink.blink.Realm.RealmChattingRoom obj = realm.createObjectInternal(com.blink.blink.Realm.RealmChattingRoom.class, true, excludeFields);
        if (json.has("matchingId")) {
            if (json.isNull("matchingId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'matchingId' to null.");
            } else {
                ((RealmChattingRoomRealmProxyInterface) obj).realmSet$matchingId((long) json.getLong("matchingId"));
            }
        }
        if (json.has("matchedUser")) {
            if (json.isNull("matchedUser")) {
                ((RealmChattingRoomRealmProxyInterface) obj).realmSet$matchedUser(null);
            } else {
                com.blink.blink.Realm.RealmUser matchedUserObj = RealmUserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("matchedUser"), update);
                ((RealmChattingRoomRealmProxyInterface) obj).realmSet$matchedUser(matchedUserObj);
            }
        }
        if (json.has("messages")) {
            if (json.isNull("messages")) {
                ((RealmChattingRoomRealmProxyInterface) obj).realmSet$messages(null);
            } else {
                ((RealmChattingRoomRealmProxyInterface) obj).realmGet$messages().clear();
                JSONArray array = json.getJSONArray("messages");
                for (int i = 0; i < array.length(); i++) {
                    com.blink.blink.Realm.RealmMessage item = RealmMessageRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((RealmChattingRoomRealmProxyInterface) obj).realmGet$messages().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.blink.blink.Realm.RealmChattingRoom createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.blink.blink.Realm.RealmChattingRoom obj = new com.blink.blink.Realm.RealmChattingRoom();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("matchingId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'matchingId' to null.");
                } else {
                    ((RealmChattingRoomRealmProxyInterface) obj).realmSet$matchingId((long) reader.nextLong());
                }
            } else if (name.equals("matchedUser")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((RealmChattingRoomRealmProxyInterface) obj).realmSet$matchedUser(null);
                } else {
                    com.blink.blink.Realm.RealmUser matchedUserObj = RealmUserRealmProxy.createUsingJsonStream(realm, reader);
                    ((RealmChattingRoomRealmProxyInterface) obj).realmSet$matchedUser(matchedUserObj);
                }
            } else if (name.equals("messages")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((RealmChattingRoomRealmProxyInterface) obj).realmSet$messages(null);
                } else {
                    ((RealmChattingRoomRealmProxyInterface) obj).realmSet$messages(new RealmList<com.blink.blink.Realm.RealmMessage>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.blink.blink.Realm.RealmMessage item = RealmMessageRealmProxy.createUsingJsonStream(realm, reader);
                        ((RealmChattingRoomRealmProxyInterface) obj).realmGet$messages().add(item);
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

    public static com.blink.blink.Realm.RealmChattingRoom copyOrUpdate(Realm realm, com.blink.blink.Realm.RealmChattingRoom object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.blink.blink.Realm.RealmChattingRoom) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.blink.blink.Realm.RealmChattingRoom copy(Realm realm, com.blink.blink.Realm.RealmChattingRoom newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.blink.blink.Realm.RealmChattingRoom) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.blink.blink.Realm.RealmChattingRoom realmObject = realm.createObjectInternal(com.blink.blink.Realm.RealmChattingRoom.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((RealmChattingRoomRealmProxyInterface) realmObject).realmSet$matchingId(((RealmChattingRoomRealmProxyInterface) newObject).realmGet$matchingId());

            com.blink.blink.Realm.RealmUser matchedUserObj = ((RealmChattingRoomRealmProxyInterface) newObject).realmGet$matchedUser();
            if (matchedUserObj != null) {
                com.blink.blink.Realm.RealmUser cachematchedUser = (com.blink.blink.Realm.RealmUser) cache.get(matchedUserObj);
                if (cachematchedUser != null) {
                    ((RealmChattingRoomRealmProxyInterface) realmObject).realmSet$matchedUser(cachematchedUser);
                } else {
                    ((RealmChattingRoomRealmProxyInterface) realmObject).realmSet$matchedUser(RealmUserRealmProxy.copyOrUpdate(realm, matchedUserObj, update, cache));
                }
            } else {
                ((RealmChattingRoomRealmProxyInterface) realmObject).realmSet$matchedUser(null);
            }

            RealmList<com.blink.blink.Realm.RealmMessage> messagesList = ((RealmChattingRoomRealmProxyInterface) newObject).realmGet$messages();
            if (messagesList != null) {
                RealmList<com.blink.blink.Realm.RealmMessage> messagesRealmList = ((RealmChattingRoomRealmProxyInterface) realmObject).realmGet$messages();
                for (int i = 0; i < messagesList.size(); i++) {
                    com.blink.blink.Realm.RealmMessage messagesItem = messagesList.get(i);
                    com.blink.blink.Realm.RealmMessage cachemessages = (com.blink.blink.Realm.RealmMessage) cache.get(messagesItem);
                    if (cachemessages != null) {
                        messagesRealmList.add(cachemessages);
                    } else {
                        messagesRealmList.add(RealmMessageRealmProxy.copyOrUpdate(realm, messagesList.get(i), update, cache));
                    }
                }
            }

            return realmObject;
        }
    }

    public static long insert(Realm realm, com.blink.blink.Realm.RealmChattingRoom object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.blink.blink.Realm.RealmChattingRoom.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmChattingRoomColumnInfo columnInfo = (RealmChattingRoomColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmChattingRoom.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.matchingIdIndex, rowIndex, ((RealmChattingRoomRealmProxyInterface)object).realmGet$matchingId(), false);

        com.blink.blink.Realm.RealmUser matchedUserObj = ((RealmChattingRoomRealmProxyInterface) object).realmGet$matchedUser();
        if (matchedUserObj != null) {
            Long cachematchedUser = cache.get(matchedUserObj);
            if (cachematchedUser == null) {
                cachematchedUser = RealmUserRealmProxy.insert(realm, matchedUserObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.matchedUserIndex, rowIndex, cachematchedUser, false);
        }

        RealmList<com.blink.blink.Realm.RealmMessage> messagesList = ((RealmChattingRoomRealmProxyInterface) object).realmGet$messages();
        if (messagesList != null) {
            long messagesNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messagesIndex, rowIndex);
            for (com.blink.blink.Realm.RealmMessage messagesItem : messagesList) {
                Long cacheItemIndexmessages = cache.get(messagesItem);
                if (cacheItemIndexmessages == null) {
                    cacheItemIndexmessages = RealmMessageRealmProxy.insert(realm, messagesItem, cache);
                }
                LinkView.nativeAdd(messagesNativeLinkViewPtr, cacheItemIndexmessages);
            }
            LinkView.nativeClose(messagesNativeLinkViewPtr);
        }

        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.blink.blink.Realm.RealmChattingRoom.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmChattingRoomColumnInfo columnInfo = (RealmChattingRoomColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmChattingRoom.class);
        com.blink.blink.Realm.RealmChattingRoom object = null;
        while (objects.hasNext()) {
            object = (com.blink.blink.Realm.RealmChattingRoom) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                Table.nativeSetLong(tableNativePtr, columnInfo.matchingIdIndex, rowIndex, ((RealmChattingRoomRealmProxyInterface)object).realmGet$matchingId(), false);

                com.blink.blink.Realm.RealmUser matchedUserObj = ((RealmChattingRoomRealmProxyInterface) object).realmGet$matchedUser();
                if (matchedUserObj != null) {
                    Long cachematchedUser = cache.get(matchedUserObj);
                    if (cachematchedUser == null) {
                        cachematchedUser = RealmUserRealmProxy.insert(realm, matchedUserObj, cache);
                    }
                    table.setLink(columnInfo.matchedUserIndex, rowIndex, cachematchedUser, false);
                }

                RealmList<com.blink.blink.Realm.RealmMessage> messagesList = ((RealmChattingRoomRealmProxyInterface) object).realmGet$messages();
                if (messagesList != null) {
                    long messagesNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messagesIndex, rowIndex);
                    for (com.blink.blink.Realm.RealmMessage messagesItem : messagesList) {
                        Long cacheItemIndexmessages = cache.get(messagesItem);
                        if (cacheItemIndexmessages == null) {
                            cacheItemIndexmessages = RealmMessageRealmProxy.insert(realm, messagesItem, cache);
                        }
                        LinkView.nativeAdd(messagesNativeLinkViewPtr, cacheItemIndexmessages);
                    }
                    LinkView.nativeClose(messagesNativeLinkViewPtr);
                }

            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.blink.blink.Realm.RealmChattingRoom object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.blink.blink.Realm.RealmChattingRoom.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmChattingRoomColumnInfo columnInfo = (RealmChattingRoomColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmChattingRoom.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.matchingIdIndex, rowIndex, ((RealmChattingRoomRealmProxyInterface)object).realmGet$matchingId(), false);

        com.blink.blink.Realm.RealmUser matchedUserObj = ((RealmChattingRoomRealmProxyInterface) object).realmGet$matchedUser();
        if (matchedUserObj != null) {
            Long cachematchedUser = cache.get(matchedUserObj);
            if (cachematchedUser == null) {
                cachematchedUser = RealmUserRealmProxy.insertOrUpdate(realm, matchedUserObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.matchedUserIndex, rowIndex, cachematchedUser, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.matchedUserIndex, rowIndex);
        }

        long messagesNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messagesIndex, rowIndex);
        LinkView.nativeClear(messagesNativeLinkViewPtr);
        RealmList<com.blink.blink.Realm.RealmMessage> messagesList = ((RealmChattingRoomRealmProxyInterface) object).realmGet$messages();
        if (messagesList != null) {
            for (com.blink.blink.Realm.RealmMessage messagesItem : messagesList) {
                Long cacheItemIndexmessages = cache.get(messagesItem);
                if (cacheItemIndexmessages == null) {
                    cacheItemIndexmessages = RealmMessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                }
                LinkView.nativeAdd(messagesNativeLinkViewPtr, cacheItemIndexmessages);
            }
        }
        LinkView.nativeClose(messagesNativeLinkViewPtr);

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.blink.blink.Realm.RealmChattingRoom.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmChattingRoomColumnInfo columnInfo = (RealmChattingRoomColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmChattingRoom.class);
        com.blink.blink.Realm.RealmChattingRoom object = null;
        while (objects.hasNext()) {
            object = (com.blink.blink.Realm.RealmChattingRoom) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                Table.nativeSetLong(tableNativePtr, columnInfo.matchingIdIndex, rowIndex, ((RealmChattingRoomRealmProxyInterface)object).realmGet$matchingId(), false);

                com.blink.blink.Realm.RealmUser matchedUserObj = ((RealmChattingRoomRealmProxyInterface) object).realmGet$matchedUser();
                if (matchedUserObj != null) {
                    Long cachematchedUser = cache.get(matchedUserObj);
                    if (cachematchedUser == null) {
                        cachematchedUser = RealmUserRealmProxy.insertOrUpdate(realm, matchedUserObj, cache);
                    }
                    Table.nativeSetLink(tableNativePtr, columnInfo.matchedUserIndex, rowIndex, cachematchedUser, false);
                } else {
                    Table.nativeNullifyLink(tableNativePtr, columnInfo.matchedUserIndex, rowIndex);
                }

                long messagesNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messagesIndex, rowIndex);
                LinkView.nativeClear(messagesNativeLinkViewPtr);
                RealmList<com.blink.blink.Realm.RealmMessage> messagesList = ((RealmChattingRoomRealmProxyInterface) object).realmGet$messages();
                if (messagesList != null) {
                    for (com.blink.blink.Realm.RealmMessage messagesItem : messagesList) {
                        Long cacheItemIndexmessages = cache.get(messagesItem);
                        if (cacheItemIndexmessages == null) {
                            cacheItemIndexmessages = RealmMessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                        }
                        LinkView.nativeAdd(messagesNativeLinkViewPtr, cacheItemIndexmessages);
                    }
                }
                LinkView.nativeClose(messagesNativeLinkViewPtr);

            }
        }
    }

    public static com.blink.blink.Realm.RealmChattingRoom createDetachedCopy(com.blink.blink.Realm.RealmChattingRoom realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.blink.blink.Realm.RealmChattingRoom unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.blink.blink.Realm.RealmChattingRoom)cachedObject.object;
            } else {
                unmanagedObject = (com.blink.blink.Realm.RealmChattingRoom)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.blink.blink.Realm.RealmChattingRoom();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((RealmChattingRoomRealmProxyInterface) unmanagedObject).realmSet$matchingId(((RealmChattingRoomRealmProxyInterface) realmObject).realmGet$matchingId());

        // Deep copy of matchedUser
        ((RealmChattingRoomRealmProxyInterface) unmanagedObject).realmSet$matchedUser(RealmUserRealmProxy.createDetachedCopy(((RealmChattingRoomRealmProxyInterface) realmObject).realmGet$matchedUser(), currentDepth + 1, maxDepth, cache));

        // Deep copy of messages
        if (currentDepth == maxDepth) {
            ((RealmChattingRoomRealmProxyInterface) unmanagedObject).realmSet$messages(null);
        } else {
            RealmList<com.blink.blink.Realm.RealmMessage> managedmessagesList = ((RealmChattingRoomRealmProxyInterface) realmObject).realmGet$messages();
            RealmList<com.blink.blink.Realm.RealmMessage> unmanagedmessagesList = new RealmList<com.blink.blink.Realm.RealmMessage>();
            ((RealmChattingRoomRealmProxyInterface) unmanagedObject).realmSet$messages(unmanagedmessagesList);
            int nextDepth = currentDepth + 1;
            int size = managedmessagesList.size();
            for (int i = 0; i < size; i++) {
                com.blink.blink.Realm.RealmMessage item = RealmMessageRealmProxy.createDetachedCopy(managedmessagesList.get(i), nextDepth, maxDepth, cache);
                unmanagedmessagesList.add(item);
            }
        }
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmChattingRoom = [");
        stringBuilder.append("{matchingId:");
        stringBuilder.append(realmGet$matchingId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{matchedUser:");
        stringBuilder.append(realmGet$matchedUser() != null ? "RealmUser" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{messages:");
        stringBuilder.append("RealmList<RealmMessage>[").append(realmGet$messages().size()).append("]");
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
        RealmChattingRoomRealmProxy aRealmChattingRoom = (RealmChattingRoomRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aRealmChattingRoom.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmChattingRoom.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aRealmChattingRoom.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
