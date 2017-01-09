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

public class RealmMessageRealmProxy extends com.blink.blink.Realm.RealmMessage
    implements RealmObjectProxy, RealmMessageRealmProxyInterface {

    static final class RealmMessageColumnInfo extends ColumnInfo
        implements Cloneable {

        public long userIndex;
        public long textIndex;
        public long timeIndex;
        public long isReadIndex;
        public long isSentIndex;

        RealmMessageColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(5);
            this.userIndex = getValidColumnIndex(path, table, "RealmMessage", "user");
            indicesMap.put("user", this.userIndex);
            this.textIndex = getValidColumnIndex(path, table, "RealmMessage", "text");
            indicesMap.put("text", this.textIndex);
            this.timeIndex = getValidColumnIndex(path, table, "RealmMessage", "time");
            indicesMap.put("time", this.timeIndex);
            this.isReadIndex = getValidColumnIndex(path, table, "RealmMessage", "isRead");
            indicesMap.put("isRead", this.isReadIndex);
            this.isSentIndex = getValidColumnIndex(path, table, "RealmMessage", "isSent");
            indicesMap.put("isSent", this.isSentIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final RealmMessageColumnInfo otherInfo = (RealmMessageColumnInfo) other;
            this.userIndex = otherInfo.userIndex;
            this.textIndex = otherInfo.textIndex;
            this.timeIndex = otherInfo.timeIndex;
            this.isReadIndex = otherInfo.isReadIndex;
            this.isSentIndex = otherInfo.isSentIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final RealmMessageColumnInfo clone() {
            return (RealmMessageColumnInfo) super.clone();
        }

    }
    private RealmMessageColumnInfo columnInfo;
    private ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("user");
        fieldNames.add("text");
        fieldNames.add("time");
        fieldNames.add("isRead");
        fieldNames.add("isSent");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmMessageRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmMessageColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(com.blink.blink.Realm.RealmMessage.class, this);
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

    @SuppressWarnings("cast")
    public String realmGet$text() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.textIndex);
    }

    public void realmSet$text(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.textIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.textIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.textIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.textIndex, value);
    }

    @SuppressWarnings("cast")
    public Date realmGet$time() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.timeIndex)) {
            return null;
        }
        return (java.util.Date) proxyState.getRow$realm().getDate(columnInfo.timeIndex);
    }

    public void realmSet$time(Date value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.timeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDate(columnInfo.timeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.timeIndex);
            return;
        }
        proxyState.getRow$realm().setDate(columnInfo.timeIndex, value);
    }

    @SuppressWarnings("cast")
    public boolean realmGet$isRead() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isReadIndex);
    }

    public void realmSet$isRead(boolean value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.isReadIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.isReadIndex, value);
    }

    @SuppressWarnings("cast")
    public boolean realmGet$isSent() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isSentIndex);
    }

    public void realmSet$isSent(boolean value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.isSentIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.isSentIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("RealmMessage")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("RealmMessage");
            if (!realmSchema.contains("RealmUser")) {
                RealmUserRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add(new Property("user", RealmFieldType.OBJECT, realmSchema.get("RealmUser")));
            realmObjectSchema.add(new Property("text", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("time", RealmFieldType.DATE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("isRead", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("isSent", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("RealmMessage");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_RealmMessage")) {
            Table table = sharedRealm.getTable("class_RealmMessage");
            if (!sharedRealm.hasTable("class_RealmUser")) {
                RealmUserRealmProxy.initTable(sharedRealm);
            }
            table.addColumnLink(RealmFieldType.OBJECT, "user", sharedRealm.getTable("class_RealmUser"));
            table.addColumn(RealmFieldType.STRING, "text", Table.NULLABLE);
            table.addColumn(RealmFieldType.DATE, "time", Table.NULLABLE);
            table.addColumn(RealmFieldType.BOOLEAN, "isRead", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.BOOLEAN, "isSent", Table.NOT_NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return sharedRealm.getTable("class_RealmMessage");
    }

    public static RealmMessageColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_RealmMessage")) {
            Table table = sharedRealm.getTable("class_RealmMessage");
            final long columnCount = table.getColumnCount();
            if (columnCount != 5) {
                if (columnCount < 5) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 5 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 5 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 5 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmMessageColumnInfo columnInfo = new RealmMessageColumnInfo(sharedRealm.getPath(), table);

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
            if (!columnTypes.containsKey("text")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'text' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("text") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'text' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.textIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'text' is required. Either set @Required to field 'text' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("time")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'time' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("time") != RealmFieldType.DATE) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Date' for field 'time' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.timeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'time' is required. Either set @Required to field 'time' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("isRead")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'isRead' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("isRead") != RealmFieldType.BOOLEAN) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'isRead' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.isReadIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'isRead' does support null values in the existing Realm file. Use corresponding boxed type for field 'isRead' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("isSent")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'isSent' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("isSent") != RealmFieldType.BOOLEAN) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'isSent' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.isSentIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'isSent' does support null values in the existing Realm file. Use corresponding boxed type for field 'isSent' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'RealmMessage' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmMessage";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.blink.blink.Realm.RealmMessage createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        if (json.has("user")) {
            excludeFields.add("user");
        }
        com.blink.blink.Realm.RealmMessage obj = realm.createObjectInternal(com.blink.blink.Realm.RealmMessage.class, true, excludeFields);
        if (json.has("user")) {
            if (json.isNull("user")) {
                ((RealmMessageRealmProxyInterface) obj).realmSet$user(null);
            } else {
                com.blink.blink.Realm.RealmUser userObj = RealmUserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("user"), update);
                ((RealmMessageRealmProxyInterface) obj).realmSet$user(userObj);
            }
        }
        if (json.has("text")) {
            if (json.isNull("text")) {
                ((RealmMessageRealmProxyInterface) obj).realmSet$text(null);
            } else {
                ((RealmMessageRealmProxyInterface) obj).realmSet$text((String) json.getString("text"));
            }
        }
        if (json.has("time")) {
            if (json.isNull("time")) {
                ((RealmMessageRealmProxyInterface) obj).realmSet$time(null);
            } else {
                Object timestamp = json.get("time");
                if (timestamp instanceof String) {
                    ((RealmMessageRealmProxyInterface) obj).realmSet$time(JsonUtils.stringToDate((String) timestamp));
                } else {
                    ((RealmMessageRealmProxyInterface) obj).realmSet$time(new Date(json.getLong("time")));
                }
            }
        }
        if (json.has("isRead")) {
            if (json.isNull("isRead")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isRead' to null.");
            } else {
                ((RealmMessageRealmProxyInterface) obj).realmSet$isRead((boolean) json.getBoolean("isRead"));
            }
        }
        if (json.has("isSent")) {
            if (json.isNull("isSent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSent' to null.");
            } else {
                ((RealmMessageRealmProxyInterface) obj).realmSet$isSent((boolean) json.getBoolean("isSent"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.blink.blink.Realm.RealmMessage createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.blink.blink.Realm.RealmMessage obj = new com.blink.blink.Realm.RealmMessage();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("user")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((RealmMessageRealmProxyInterface) obj).realmSet$user(null);
                } else {
                    com.blink.blink.Realm.RealmUser userObj = RealmUserRealmProxy.createUsingJsonStream(realm, reader);
                    ((RealmMessageRealmProxyInterface) obj).realmSet$user(userObj);
                }
            } else if (name.equals("text")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((RealmMessageRealmProxyInterface) obj).realmSet$text(null);
                } else {
                    ((RealmMessageRealmProxyInterface) obj).realmSet$text((String) reader.nextString());
                }
            } else if (name.equals("time")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((RealmMessageRealmProxyInterface) obj).realmSet$time(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        ((RealmMessageRealmProxyInterface) obj).realmSet$time(new Date(timestamp));
                    }
                } else {
                    ((RealmMessageRealmProxyInterface) obj).realmSet$time(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("isRead")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isRead' to null.");
                } else {
                    ((RealmMessageRealmProxyInterface) obj).realmSet$isRead((boolean) reader.nextBoolean());
                }
            } else if (name.equals("isSent")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSent' to null.");
                } else {
                    ((RealmMessageRealmProxyInterface) obj).realmSet$isSent((boolean) reader.nextBoolean());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.blink.blink.Realm.RealmMessage copyOrUpdate(Realm realm, com.blink.blink.Realm.RealmMessage object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.blink.blink.Realm.RealmMessage) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.blink.blink.Realm.RealmMessage copy(Realm realm, com.blink.blink.Realm.RealmMessage newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.blink.blink.Realm.RealmMessage) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.blink.blink.Realm.RealmMessage realmObject = realm.createObjectInternal(com.blink.blink.Realm.RealmMessage.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);

            com.blink.blink.Realm.RealmUser userObj = ((RealmMessageRealmProxyInterface) newObject).realmGet$user();
            if (userObj != null) {
                com.blink.blink.Realm.RealmUser cacheuser = (com.blink.blink.Realm.RealmUser) cache.get(userObj);
                if (cacheuser != null) {
                    ((RealmMessageRealmProxyInterface) realmObject).realmSet$user(cacheuser);
                } else {
                    ((RealmMessageRealmProxyInterface) realmObject).realmSet$user(RealmUserRealmProxy.copyOrUpdate(realm, userObj, update, cache));
                }
            } else {
                ((RealmMessageRealmProxyInterface) realmObject).realmSet$user(null);
            }
            ((RealmMessageRealmProxyInterface) realmObject).realmSet$text(((RealmMessageRealmProxyInterface) newObject).realmGet$text());
            ((RealmMessageRealmProxyInterface) realmObject).realmSet$time(((RealmMessageRealmProxyInterface) newObject).realmGet$time());
            ((RealmMessageRealmProxyInterface) realmObject).realmSet$isRead(((RealmMessageRealmProxyInterface) newObject).realmGet$isRead());
            ((RealmMessageRealmProxyInterface) realmObject).realmSet$isSent(((RealmMessageRealmProxyInterface) newObject).realmGet$isSent());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.blink.blink.Realm.RealmMessage object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.blink.blink.Realm.RealmMessage.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmMessageColumnInfo columnInfo = (RealmMessageColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmMessage.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);

        com.blink.blink.Realm.RealmUser userObj = ((RealmMessageRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = RealmUserRealmProxy.insert(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        }
        String realmGet$text = ((RealmMessageRealmProxyInterface)object).realmGet$text();
        if (realmGet$text != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.textIndex, rowIndex, realmGet$text, false);
        }
        java.util.Date realmGet$time = ((RealmMessageRealmProxyInterface)object).realmGet$time();
        if (realmGet$time != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.timeIndex, rowIndex, realmGet$time.getTime(), false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isReadIndex, rowIndex, ((RealmMessageRealmProxyInterface)object).realmGet$isRead(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isSentIndex, rowIndex, ((RealmMessageRealmProxyInterface)object).realmGet$isSent(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.blink.blink.Realm.RealmMessage.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmMessageColumnInfo columnInfo = (RealmMessageColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmMessage.class);
        com.blink.blink.Realm.RealmMessage object = null;
        while (objects.hasNext()) {
            object = (com.blink.blink.Realm.RealmMessage) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);

                com.blink.blink.Realm.RealmUser userObj = ((RealmMessageRealmProxyInterface) object).realmGet$user();
                if (userObj != null) {
                    Long cacheuser = cache.get(userObj);
                    if (cacheuser == null) {
                        cacheuser = RealmUserRealmProxy.insert(realm, userObj, cache);
                    }
                    table.setLink(columnInfo.userIndex, rowIndex, cacheuser, false);
                }
                String realmGet$text = ((RealmMessageRealmProxyInterface)object).realmGet$text();
                if (realmGet$text != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.textIndex, rowIndex, realmGet$text, false);
                }
                java.util.Date realmGet$time = ((RealmMessageRealmProxyInterface)object).realmGet$time();
                if (realmGet$time != null) {
                    Table.nativeSetTimestamp(tableNativePtr, columnInfo.timeIndex, rowIndex, realmGet$time.getTime(), false);
                }
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isReadIndex, rowIndex, ((RealmMessageRealmProxyInterface)object).realmGet$isRead(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isSentIndex, rowIndex, ((RealmMessageRealmProxyInterface)object).realmGet$isSent(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.blink.blink.Realm.RealmMessage object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.blink.blink.Realm.RealmMessage.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmMessageColumnInfo columnInfo = (RealmMessageColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmMessage.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);

        com.blink.blink.Realm.RealmUser userObj = ((RealmMessageRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = RealmUserRealmProxy.insertOrUpdate(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
        }
        String realmGet$text = ((RealmMessageRealmProxyInterface)object).realmGet$text();
        if (realmGet$text != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.textIndex, rowIndex, realmGet$text, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.textIndex, rowIndex, false);
        }
        java.util.Date realmGet$time = ((RealmMessageRealmProxyInterface)object).realmGet$time();
        if (realmGet$time != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.timeIndex, rowIndex, realmGet$time.getTime(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.timeIndex, rowIndex, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isReadIndex, rowIndex, ((RealmMessageRealmProxyInterface)object).realmGet$isRead(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isSentIndex, rowIndex, ((RealmMessageRealmProxyInterface)object).realmGet$isSent(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.blink.blink.Realm.RealmMessage.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmMessageColumnInfo columnInfo = (RealmMessageColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmMessage.class);
        com.blink.blink.Realm.RealmMessage object = null;
        while (objects.hasNext()) {
            object = (com.blink.blink.Realm.RealmMessage) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);

                com.blink.blink.Realm.RealmUser userObj = ((RealmMessageRealmProxyInterface) object).realmGet$user();
                if (userObj != null) {
                    Long cacheuser = cache.get(userObj);
                    if (cacheuser == null) {
                        cacheuser = RealmUserRealmProxy.insertOrUpdate(realm, userObj, cache);
                    }
                    Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
                } else {
                    Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
                }
                String realmGet$text = ((RealmMessageRealmProxyInterface)object).realmGet$text();
                if (realmGet$text != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.textIndex, rowIndex, realmGet$text, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.textIndex, rowIndex, false);
                }
                java.util.Date realmGet$time = ((RealmMessageRealmProxyInterface)object).realmGet$time();
                if (realmGet$time != null) {
                    Table.nativeSetTimestamp(tableNativePtr, columnInfo.timeIndex, rowIndex, realmGet$time.getTime(), false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.timeIndex, rowIndex, false);
                }
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isReadIndex, rowIndex, ((RealmMessageRealmProxyInterface)object).realmGet$isRead(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.isSentIndex, rowIndex, ((RealmMessageRealmProxyInterface)object).realmGet$isSent(), false);
            }
        }
    }

    public static com.blink.blink.Realm.RealmMessage createDetachedCopy(com.blink.blink.Realm.RealmMessage realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.blink.blink.Realm.RealmMessage unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.blink.blink.Realm.RealmMessage)cachedObject.object;
            } else {
                unmanagedObject = (com.blink.blink.Realm.RealmMessage)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.blink.blink.Realm.RealmMessage();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }

        // Deep copy of user
        ((RealmMessageRealmProxyInterface) unmanagedObject).realmSet$user(RealmUserRealmProxy.createDetachedCopy(((RealmMessageRealmProxyInterface) realmObject).realmGet$user(), currentDepth + 1, maxDepth, cache));
        ((RealmMessageRealmProxyInterface) unmanagedObject).realmSet$text(((RealmMessageRealmProxyInterface) realmObject).realmGet$text());
        ((RealmMessageRealmProxyInterface) unmanagedObject).realmSet$time(((RealmMessageRealmProxyInterface) realmObject).realmGet$time());
        ((RealmMessageRealmProxyInterface) unmanagedObject).realmSet$isRead(((RealmMessageRealmProxyInterface) realmObject).realmGet$isRead());
        ((RealmMessageRealmProxyInterface) unmanagedObject).realmSet$isSent(((RealmMessageRealmProxyInterface) realmObject).realmGet$isSent());
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmMessage = [");
        stringBuilder.append("{user:");
        stringBuilder.append(realmGet$user() != null ? "RealmUser" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{text:");
        stringBuilder.append(realmGet$text() != null ? realmGet$text() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time:");
        stringBuilder.append(realmGet$time() != null ? realmGet$time() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isRead:");
        stringBuilder.append(realmGet$isRead());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isSent:");
        stringBuilder.append(realmGet$isSent());
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
        RealmMessageRealmProxy aRealmMessage = (RealmMessageRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aRealmMessage.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmMessage.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aRealmMessage.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
