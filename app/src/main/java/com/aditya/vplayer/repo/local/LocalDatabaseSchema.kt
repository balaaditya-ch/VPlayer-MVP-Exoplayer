package com.aditya.vplayer.repo.local

import androidx.room.*
import com.google.gson.annotations.SerializedName


class LocalDatabaseSchema {


    @Entity(tableName = "Videos")
    class VideoMetaEntity {

        @SerializedName("id")
        @PrimaryKey
        var id = 0L

        @SerializedName("description")
        var description = ""

        @SerializedName("thumb")
        var thumb = ""

        @SerializedName("title")
        var title = ""

        @SerializedName("url")
        var url = ""

        @SerializedName("playback")
        var playback = 0L


    }

    @Dao
    abstract class VideoMetaListDao {

        @Query("SELECT * FROM  Videos")
        abstract fun getAll(): List<VideoMetaEntity>

        @Query("SELECT * FROM  Videos WHERE id != :id ORDER BY id DESC")
        abstract fun getLocalVideos(id: Long): List<VideoMetaEntity>

        @Query("UPDATE Videos SET playback = :playback WHERE id = :Id")
        abstract fun updateAlertStatus(Id: Long, playback: Long)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        abstract fun insertAll(videos: ArrayList<VideoMetaEntity>)

        @Query("SELECT COUNT(*) FROM Videos")
        abstract fun getRowsCount(): Long

        @Query("SELECT playback FROM Videos WHERE id = :Id")
        abstract fun getPlayBackById(Id: Long): Long

    }
}