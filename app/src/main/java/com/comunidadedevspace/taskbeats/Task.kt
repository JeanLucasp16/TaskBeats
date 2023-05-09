package com.comunidadedevspace.taskbeats

import android.app.ActivityManager.TaskDescription
import android.icu.text.CaseMap
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title: String,
    val description: String):  Serializable

