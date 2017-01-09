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

public class RealmUserRealmProxy extends com.blink.blink.Realm.RealmUser
    implements RealmObjectProxy, RealmUserRealmProxyInterface {

    static final class RealmUserColumnInfo extends ColumnInfo
        implements Cloneable {

        public long idIndex;
        public long nameIndex;
        public long imageURLIndex;

        RealmUserColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.idIndex = getValidColumnIndex(path, table, "RealmUser", "id");
            indicesMap.put("id", this.idIndex);
            this.nameIndex = getValidColumnIndex(path, table, "RealmUser", "name");
            indicesMap.put("name", this.nameIndex);
            this.imageURLIndex = getValidColumnIndex(path, table, "RealmUser", "imageURL");
            indicesMap.put("imageURL", this.imageURLIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final RealmUserColumnInfo otherInfo = (RealmUserColumnInfo) other;
            this.idIndex = otherInfo.idIndex;
            this.nameIndex = otherInfo.nameIndex;
            this.imageURLIndex = otherInfo.imageURLIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final RealmUserColumnInfo clone() {
            return (RealmUserColumnInfo) super.clone();
        }

    }
    private RealmUserColumnInfo columnInfo;
    private ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("imageURL");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmUserRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmUserColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(com.blink.blink.Realm.RealmUser.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public long realmGet$id() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    public void realmSet$id(long value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.idIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$name() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    public void realmSet$name(String value) {
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
                row.getTable().setNull(columnInfo.nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$imageURL() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imageURLIndex);
    }

    public void realmSet$imageURL(String value) {
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
                row.getTable().setNull(columnInfo.imageURLIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.imageURLIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.imageURLIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.imageURLIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("RealmUser")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("RealmUser");
            realmObjectSchema.add(new Property("id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("imageURL", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("RealmUser");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_RealmUser")) {
            Table table = sharedRealm.getTable("class_RealmUser");
            table.addColumn(RealmFieldType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "name", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "imageURL", Table.NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return sharedRealm.getTable("class_RealmUser");
    }

    public static RealmUserColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_RealmUser")) {
            Table table = sharedRealm.getTable("class_RealmUser");
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

            final RealmUserColumnInfo columnInfo = new RealmUserColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("id") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'id' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.idIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'id' does support null values in the existing Realm file. Use corresponding boxed type for field 'id' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("name") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.nameIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'name' is required. Either set @Required to field 'name' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("imageURL")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'imageURL' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("imageURL") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'imageURL' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.imageURLIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'imageURL' is required. Either set @Required to field 'imageURL' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'RealmUser' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmUser";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.blink.blink.Realm.RealmUser createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.blink.blink.Realm.RealmUser obj = realm.createObjectInternal(com.blink.blink.Realm.RealmUser.class, true, excludeFields);
        if (json.has("id")) {
            if (json.isNull("id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
            } else {
                ((RealmUserRealmProxyInterface) obj).realmSet$id((long) json.getLong("id"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                ((RealmUserRealmProxyInterface) obj).realmSet$name(null);
            } else {
                ((RealmUserRealmProxyInterface) obj).realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("imageURL")) {
            if (json.isNull("imageURL")) {
                ((RealmUserRealmProxyInterface) obj).realmSet$imageURL(null);
            } else {
                ((RealmUserRealmProxyInterface) obj).realmSet$imageURL((String) json.getString("imageURL"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.blink.blink.Realm.RealmUser createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.blink.blink.Realm.RealmUser obj = new com.blink.blink.Realm.RealmUser();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                } else {
                    ((RealmUserRealmProxyInterface) obj).realmSet$id((long) reader.nextLong());
                }
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((RealmUserRealmProxyInterface) obj).realmSet$name(null);
                } else {
                    ((RealmUserRealmProxyInterface) obj).realmSet$name((String) reader.nextString());
                }
            } else if (name.equals("imageURL")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((RealmUserRealmProxyInterface) obj).realmSet$imageURL(null);
                } else {
                    ((RealmUserRealmProxyInterface) obj).realmSet$imageURL((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.blink.blink.Realm.RealmUser copyOrUpdate(Realm realm, com.blink.blink.Realm.RealmUser object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.blink.blink.Realm.RealmUser) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.blink.blink.Realm.RealmUser copy(Realm realm, com.blink.blink.Realm.RealmUser newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.blink.blink.Realm.RealmUser) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.blink.blink.Realm.RealmUser realmObject = realm.createObjectInternal(com.blink.blink.Realm.RealmUser.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((RealmUserRealmProxyInterface) realmObject).realmSet$id(((RealmUserRealmProxyInterface) newObject).realmGet$id());
            ((RealmUserRealmProxyInterface) realmObject).realmSet$name(((RealmUserRealmProxyInterface) newObject).realmGet$name());
            ((RealmUserRealmProxyInterface) realmObject).realmSet$imageURL(((RealmUserRealmProxyInterface) newObject).realmGet$imageURL());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.blink.blink.Realm.RealmUser object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.blink.blink.Realm.RealmUser.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmUserColumnInfo columnInfo = (RealmUserColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmUser.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, ((RealmUserRealmProxyInterface)object).realmGet$id(), false);
        String realmGet$name = ((RealmUserRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }
        String realmGet$imageURL = ((RealmUserRealmProxyInterface)object).realmGet$imageURL();
        if (realmGet$imageURL != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageURLIndex, rowIndex, realmGet$imageURL, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.blink.blink.Realm.RealmUser.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmUserColumnInfo columnInfo = (RealmUserColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmUser.class);
        com.blink.blink.Realm.RealmUser object = null;
        while (objects.hasNext()) {
            object = (com.blink.blink.Realm.RealmUser) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, ((RealmUserRealmProxyInterface)object).realmGet$id(), false);
                String realmGet$name = ((RealmUserRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                }
                String realmGet$imageURL = ((RealmUserRealmProxyInterface)object).realmGet$imageURL();
                if (realmGet$imageURL != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.imageURLIndex, rowIndex, realmGet$imageURL, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.blink.blink.Realm.RealmUser object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.blink.blink.Realm.RealmUser.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmUserColumnInfo columnInfo = (RealmUserColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmUser.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, ((RealmUserRealmProxyInterface)object).realmGet$id(), false);
        String realmGet$name = ((RealmUserRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }
        String realmGet$imageURL = ((RealmUserRealmProxyInterface)object).realmGet$imageURL();
        if (realmGet$imageURL != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageURLIndex, rowIndex, realmGet$imageURL, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageURLIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.blink.blink.Realm.RealmUser.class);
        long tableNativePtr = table.getNativeTablePointer();
        RealmUserColumnInfo columnInfo = (RealmUserColumnInfo) realm.schema.getColumnInfo(com.blink.blink.Realm.RealmUser.class);
        com.blink.blink.Realm.RealmUser object = null;
        while (objects.hasNext()) {
            object = (com.blink.blink.Realm.RealmUser) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, ((RealmUserRealmProxyInterface)object).realmGet$id(), false);
                String realmGet$name = ((RealmUserRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
                }
                String realmGet$imageURL = ((RealmUserRealmProxyInterface)object).realmGet$imageURL();
                if (realmGet$imageURL != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.imageURLIndex, rowIndex, realmGet$imageURL, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.imageURLIndex, rowIndex, false);
                }
            }
        }
    }

    public static com.blink.blink.Realm.RealmUser createDetachedCopy(com.blink.blink.Realm.RealmUser realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.blink.blink.Realm.RealmUser unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.blink.blink.Realm.RealmUser)cachedObject.object;
            } else {
                unmanagedObject = (com.blink.blink.Realm.RealmUser)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.blink.blink.Realm.RealmUser();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((RealmUserRealmProxyInterface) unmanagedObject).realmSet$id(((RealmUserRealmProxyInterface) realmObject).realmGet$id());
        ((RealmUserRealmProxyInterface) unmanagedObject).realmSet$name(((RealmUserRealmProxyInterface) realmObject).realmGet$name());
        ((RealmUserRealmProxyInterface) unmanagedObject).realmSet$imageURL(((RealmUserRealmProxyInterface) realmObject).realmGet$imageURL());
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmUser = [");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{imageURL:");
        stringBuilder.append(realmGet$imageURL() != null ? realmGet$imageURL() : "null");
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
        RealmUserRealmProxy aRealmUser = (RealmUserRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aRealmUser.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmUser.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aRealmUser.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
