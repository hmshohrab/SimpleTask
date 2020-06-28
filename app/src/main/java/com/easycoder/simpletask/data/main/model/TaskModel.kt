package com.easycoder.simpletask.data.main.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by HM SHOHRAB on 27,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */
@Entity(
    tableName = "Task_table",
    indices = [Index(value = ["title"], unique = false)]
)
class TaskModel(
    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String,
    @SerializedName("tItems")
    @ColumnInfo(name = "tItems")
    var tItems: Int,
    var created_at: Long = System.currentTimeMillis(),
    var updated_at: Long? = null
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}