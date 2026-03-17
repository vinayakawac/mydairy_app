package com.example.mydairy_app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.mydairy_app.core.database.Converters;
import com.example.mydairy_app.data.local.entity.EntryEntity;
import com.example.mydairy_app.data.local.entity.EntryWithTags;
import com.example.mydairy_app.data.local.entity.TagEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EntryDao_Impl implements EntryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EntryEntity> __insertionAdapterOfEntryEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<EntryEntity> __deletionAdapterOfEntryEntity;

  private final EntityDeletionOrUpdateAdapter<EntryEntity> __updateAdapterOfEntryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFtsEntry;

  private final SharedSQLiteStatement __preparedStmtOfInsertFtsEntry;

  private final SharedSQLiteStatement __preparedStmtOfDeleteEntryById;

  public EntryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEntryEntity = new EntityInsertionAdapter<EntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `entries` (`id`,`title`,`body`,`createdAt`,`updatedAt`,`photoPaths`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EntryEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        statement.bindString(3, entity.getBody());
        statement.bindLong(4, entity.getCreatedAt());
        statement.bindLong(5, entity.getUpdatedAt());
        final String _tmp = __converters.toPhotoPaths(entity.getPhotoPaths());
        statement.bindString(6, _tmp);
      }
    };
    this.__deletionAdapterOfEntryEntity = new EntityDeletionOrUpdateAdapter<EntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `entries` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EntryEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfEntryEntity = new EntityDeletionOrUpdateAdapter<EntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `entries` SET `id` = ?,`title` = ?,`body` = ?,`createdAt` = ?,`updatedAt` = ?,`photoPaths` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EntryEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        statement.bindString(3, entity.getBody());
        statement.bindLong(4, entity.getCreatedAt());
        statement.bindLong(5, entity.getUpdatedAt());
        final String _tmp = __converters.toPhotoPaths(entity.getPhotoPaths());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteFtsEntry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM entries_fts WHERE rowid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfInsertFtsEntry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "INSERT INTO entries_fts(rowid, title, body) VALUES(?, ?, ?)";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteEntryById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM entries WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertEntry(final EntryEntity entry, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEntryEntity.insertAndReturnId(entry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEntry(final EntryEntity entry,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __deletionAdapterOfEntryEntity.handle(entry);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEntry(final EntryEntity entry,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfEntryEntity.handle(entry);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFtsEntry(final long entryId,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFtsEntry.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, entryId);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteFtsEntry.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object insertFtsEntry(final long entryId, final String title, final String body,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfInsertFtsEntry.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, entryId);
        _argIndex = 2;
        if (title == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, title);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, body);
        try {
          __db.beginTransaction();
          try {
            final Long _result = _stmt.executeInsert();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfInsertFtsEntry.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEntryById(final long entryId,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteEntryById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, entryId);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteEntryById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EntryWithTags>> getAllEntries() {
    final String _sql = "SELECT * FROM entries ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"entry_tag_cross_ref", "tags",
        "entries"}, new Callable<List<EntryWithTags>>() {
      @Override
      @NonNull
      public List<EntryWithTags> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPaths");
            final LongSparseArray<ArrayList<TagEntity>> _collectionTags = new LongSparseArray<ArrayList<TagEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionTags.containsKey(_tmpKey)) {
                _collectionTags.put(_tmpKey, new ArrayList<TagEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptagsAscomExampleMydairyAppDataLocalEntityTagEntity(_collectionTags);
            final List<EntryWithTags> _result = new ArrayList<EntryWithTags>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final EntryWithTags _item;
              final EntryEntity _tmpEntry;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpBody;
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              final List<String> _tmpPhotoPaths;
              final String _tmp;
              _tmp = _cursor.getString(_cursorIndexOfPhotoPaths);
              _tmpPhotoPaths = __converters.fromPhotoPaths(_tmp);
              _tmpEntry = new EntryEntity(_tmpId,_tmpTitle,_tmpBody,_tmpCreatedAt,_tmpUpdatedAt,_tmpPhotoPaths);
              final ArrayList<TagEntity> _tmpTagsCollection;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpTagsCollection = _collectionTags.get(_tmpKey_1);
              _item = new EntryWithTags(_tmpEntry,_tmpTagsCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EntryWithTags>> getEntriesByDate(final long dateMillisStart,
      final long dateMillisEnd) {
    final String _sql = "SELECT * FROM entries WHERE createdAt BETWEEN ? AND ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, dateMillisStart);
    _argIndex = 2;
    _statement.bindLong(_argIndex, dateMillisEnd);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"entry_tag_cross_ref", "tags",
        "entries"}, new Callable<List<EntryWithTags>>() {
      @Override
      @NonNull
      public List<EntryWithTags> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPaths");
            final LongSparseArray<ArrayList<TagEntity>> _collectionTags = new LongSparseArray<ArrayList<TagEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionTags.containsKey(_tmpKey)) {
                _collectionTags.put(_tmpKey, new ArrayList<TagEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptagsAscomExampleMydairyAppDataLocalEntityTagEntity(_collectionTags);
            final List<EntryWithTags> _result = new ArrayList<EntryWithTags>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final EntryWithTags _item;
              final EntryEntity _tmpEntry;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpBody;
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              final List<String> _tmpPhotoPaths;
              final String _tmp;
              _tmp = _cursor.getString(_cursorIndexOfPhotoPaths);
              _tmpPhotoPaths = __converters.fromPhotoPaths(_tmp);
              _tmpEntry = new EntryEntity(_tmpId,_tmpTitle,_tmpBody,_tmpCreatedAt,_tmpUpdatedAt,_tmpPhotoPaths);
              final ArrayList<TagEntity> _tmpTagsCollection;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpTagsCollection = _collectionTags.get(_tmpKey_1);
              _item = new EntryWithTags(_tmpEntry,_tmpTagsCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getEntryById(final long id, final Continuation<? super EntryWithTags> $completion) {
    final String _sql = "SELECT * FROM entries WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<EntryWithTags>() {
      @Override
      @Nullable
      public EntryWithTags call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPaths");
            final LongSparseArray<ArrayList<TagEntity>> _collectionTags = new LongSparseArray<ArrayList<TagEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionTags.containsKey(_tmpKey)) {
                _collectionTags.put(_tmpKey, new ArrayList<TagEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptagsAscomExampleMydairyAppDataLocalEntityTagEntity(_collectionTags);
            final EntryWithTags _result;
            if (_cursor.moveToFirst()) {
              final EntryEntity _tmpEntry;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpBody;
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              final List<String> _tmpPhotoPaths;
              final String _tmp;
              _tmp = _cursor.getString(_cursorIndexOfPhotoPaths);
              _tmpPhotoPaths = __converters.fromPhotoPaths(_tmp);
              _tmpEntry = new EntryEntity(_tmpId,_tmpTitle,_tmpBody,_tmpCreatedAt,_tmpUpdatedAt,_tmpPhotoPaths);
              final ArrayList<TagEntity> _tmpTagsCollection;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpTagsCollection = _collectionTags.get(_tmpKey_1);
              _result = new EntryWithTags(_tmpEntry,_tmpTagsCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EntryWithTags>> searchEntries(final String query) {
    final String _sql = "\n"
            + "        SELECT entries.*\n"
            + "        FROM entries\n"
            + "        INNER JOIN entries_fts ON entries.rowid = entries_fts.rowid\n"
            + "        WHERE entries_fts MATCH ?\n"
            + "        ORDER BY entries.createdAt DESC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"entry_tag_cross_ref", "tags",
        "entries", "entries_fts"}, new Callable<List<EntryWithTags>>() {
      @Override
      @NonNull
      public List<EntryWithTags> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPaths");
            final LongSparseArray<ArrayList<TagEntity>> _collectionTags = new LongSparseArray<ArrayList<TagEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionTags.containsKey(_tmpKey)) {
                _collectionTags.put(_tmpKey, new ArrayList<TagEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptagsAscomExampleMydairyAppDataLocalEntityTagEntity(_collectionTags);
            final List<EntryWithTags> _result = new ArrayList<EntryWithTags>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final EntryWithTags _item;
              final EntryEntity _tmpEntry;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpBody;
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              final List<String> _tmpPhotoPaths;
              final String _tmp;
              _tmp = _cursor.getString(_cursorIndexOfPhotoPaths);
              _tmpPhotoPaths = __converters.fromPhotoPaths(_tmp);
              _tmpEntry = new EntryEntity(_tmpId,_tmpTitle,_tmpBody,_tmpCreatedAt,_tmpUpdatedAt,_tmpPhotoPaths);
              final ArrayList<TagEntity> _tmpTagsCollection;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpTagsCollection = _collectionTags.get(_tmpKey_1);
              _item = new EntryWithTags(_tmpEntry,_tmpTagsCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshiptagsAscomExampleMydairyAppDataLocalEntityTagEntity(
      @NonNull final LongSparseArray<ArrayList<TagEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshiptagsAscomExampleMydairyAppDataLocalEntityTagEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `tags`.`id` AS `id`,`tags`.`name` AS `name`,_junction.`entryId` FROM `entry_tag_cross_ref` AS _junction INNER JOIN `tags` ON (_junction.`tagId` = `tags`.`id`) WHERE _junction.`entryId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      // _junction.entryId;
      final int _itemKeyIndex = 2;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<TagEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final TagEntity _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final String _tmpName;
          _tmpName = _cursor.getString(_cursorIndexOfName);
          _item_1 = new TagEntity(_tmpId,_tmpName);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
