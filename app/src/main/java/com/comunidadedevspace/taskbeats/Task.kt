package com.comunidadedevspace.taskbeats

import android.app.ActivityManager.TaskDescription
import android.icu.text.CaseMap

import java.io.Serializable

data class Task(
    val id : Int,
    val title: String,
    val description: String):  Serializable

