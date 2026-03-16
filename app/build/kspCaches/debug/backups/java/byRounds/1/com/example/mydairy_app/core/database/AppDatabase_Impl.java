package com.example.mydairy_app.core.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.FtsTableInfo;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.mydairy_app.data.local.dao.EntryDao;
import com.example.mydairy_app.data.local.dao.EntryDao_Impl;
import com.example.mydairy_app.data.local.dao.EntryTagDao;
import com.example.mydairy_app.data.local.dao.EntryTagDao_Impl;
import com.example.mydairy_app.data.local.dao.TagDao;
import com.example.mydairy_app.data.local.dao.TagDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile EntryDao _entryDao;

  private volatile TagDao _tagDao;

  private volatile EntryTagDao _entryTagDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `body` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `photoPaths` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tags` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_tags_name` ON `tags` (`name`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `entry_tag_cross_ref` (`entryId` INTEGER NOT NULL, `tagId` INTEGER NOT NULL, PRIMARY KEY(`entryId`, `tagId`), FOREIGN KEY(`entryId`) REFERENCES `entries`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`tagId`) REFERENCES `tags`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `entries_fts` USING FTS4(`title` TEXT, `body` TEXT NOT NULL, content=`entries`)");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_entries_fts_BEFORE_UPDATE BEFORE UPDATE ON `entries` BEGIN DELETE FROM `entries_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_entries_fts_BEFORE_DELETE BEFORE DELETE ON `entries` BEGIN DELETE FROM `entries_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_entries_fts_AFTER_UPDATE AFTER UPDATE ON `entries` BEGIN INSERT INTO `entries_fts`(`docid`, `title`, `body`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`body`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_entries_fts_AFTER_INSERT AFTER INSERT ON `entries` BEGIN INSERT INTO `entries_fts`(`docid`, `title`, `body`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`body`); END");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'de7f81e919fd7734df20a0191b6e16b7')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `entries`");
        db.execSQL("DROP TABLE IF EXISTS `tags`");
        db.execSQL("DROP TABLE IF EXISTS `entry_tag_cross_ref`");
        db.execSQL("DROP TABLE IF EXISTS `entries_fts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_entries_fts_BEFORE_UPDATE BEFORE UPDATE ON `entries` BEGIN DELETE FROM `entries_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_entries_fts_BEFORE_DELETE BEFORE DELETE ON `entries` BEGIN DELETE FROM `entries_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_entries_fts_AFTER_UPDATE AFTER UPDATE ON `entries` BEGIN INSERT INTO `entries_fts`(`docid`, `title`, `body`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`body`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_entries_fts_AFTER_INSERT AFTER INSERT ON `entries` BEGIN INSERT INTO `entries_fts`(`docid`, `title`, `body`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`body`); END");
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsEntries = new HashMap<String, TableInfo.Column>(6);
        _columnsEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntries.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntries.put("body", new TableInfo.Column("body", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntries.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntries.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntries.put("photoPaths", new TableInfo.Column("photoPaths", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEntries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEntries = new TableInfo("entries", _columnsEntries, _foreignKeysEntries, _indicesEntries);
        final TableInfo _existingEntries = TableInfo.read(db, "entries");
        if (!_infoEntries.equals(_existingEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "entries(com.example.mydairy_app.data.local.entity.EntryEntity).\n"
                  + " Expected:\n" + _infoEntries + "\n"
                  + " Found:\n" + _existingEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsTags = new HashMap<String, TableInfo.Column>(2);
        _columnsTags.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTags = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTags = new HashSet<TableInfo.Index>(1);
        _indicesTags.add(new TableInfo.Index("index_tags_name", true, Arrays.asList("name"), Arrays.asList("ASC")));
        final TableInfo _infoTags = new TableInfo("tags", _columnsTags, _foreignKeysTags, _indicesTags);
        final TableInfo _existingTags = TableInfo.read(db, "tags");
        if (!_infoTags.equals(_existingTags)) {
          return new RoomOpenHelper.ValidationResult(false, "tags(com.example.mydairy_app.data.local.entity.TagEntity).\n"
                  + " Expected:\n" + _infoTags + "\n"
                  + " Found:\n" + _existingTags);
        }
        final HashMap<String, TableInfo.Column> _columnsEntryTagCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsEntryTagCrossRef.put("entryId", new TableInfo.Column("entryId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntryTagCrossRef.put("tagId", new TableInfo.Column("tagId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEntryTagCrossRef = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysEntryTagCrossRef.add(new TableInfo.ForeignKey("entries", "CASCADE", "NO ACTION", Arrays.asList("entryId"), Arrays.asList("id")));
        _foreignKeysEntryTagCrossRef.add(new TableInfo.ForeignKey("tags", "CASCADE", "NO ACTION", Arrays.asList("tagId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesEntryTagCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEntryTagCrossRef = new TableInfo("entry_tag_cross_ref", _columnsEntryTagCrossRef, _foreignKeysEntryTagCrossRef, _indicesEntryTagCrossRef);
        final TableInfo _existingEntryTagCrossRef = TableInfo.read(db, "entry_tag_cross_ref");
        if (!_infoEntryTagCrossRef.equals(_existingEntryTagCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "entry_tag_cross_ref(com.example.mydairy_app.data.local.entity.EntryTagCrossRef).\n"
                  + " Expected:\n" + _infoEntryTagCrossRef + "\n"
                  + " Found:\n" + _existingEntryTagCrossRef);
        }
        final HashSet<String> _columnsEntriesFts = new HashSet<String>(2);
        _columnsEntriesFts.add("title");
        _columnsEntriesFts.add("body");
        final FtsTableInfo _infoEntriesFts = new FtsTableInfo("entries_fts", _columnsEntriesFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `entries_fts` USING FTS4(`title` TEXT, `body` TEXT NOT NULL, content=`entries`)");
        final FtsTableInfo _existingEntriesFts = FtsTableInfo.read(db, "entries_fts");
        if (!_infoEntriesFts.equals(_existingEntriesFts)) {
          return new RoomOpenHelper.ValidationResult(false, "entries_fts(com.example.mydairy_app.data.local.entity.EntryFts).\n"
                  + " Expected:\n" + _infoEntriesFts + "\n"
                  + " Found:\n" + _existingEntriesFts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "de7f81e919fd7734df20a0191b6e16b7", "73c43083c20e69e7d6c4bfe19867f131");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(1);
    _shadowTablesMap.put("entries_fts", "entries");
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "entries","tags","entry_tag_cross_ref","entries_fts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `entries`");
      _db.execSQL("DELETE FROM `tags`");
      _db.execSQL("DELETE FROM `entry_tag_cross_ref`");
      _db.execSQL("DELETE FROM `entries_fts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(EntryDao.class, EntryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TagDao.class, TagDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EntryTagDao.class, EntryTagDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public EntryDao entryDao() {
    if (_entryDao != null) {
      return _entryDao;
    } else {
      synchronized(this) {
        if(_entryDao == null) {
          _entryDao = new EntryDao_Impl(this);
        }
        return _entryDao;
      }
    }
  }

  @Override
  public TagDao tagDao() {
    if (_tagDao != null) {
      return _tagDao;
    } else {
      synchronized(this) {
        if(_tagDao == null) {
          _tagDao = new TagDao_Impl(this);
        }
        return _tagDao;
      }
    }
  }

  @Override
  public EntryTagDao entryTagDao() {
    if (_entryTagDao != null) {
      return _entryTagDao;
    } else {
      synchronized(this) {
        if(_entryTagDao == null) {
          _entryTagDao = new EntryTagDao_Impl(this);
        }
        return _entryTagDao;
      }
    }
  }
}
