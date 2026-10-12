package com.projectdora.v1.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectdora.v1.core.model.TaskSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskHistoryRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("dora_task_history", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val type = object : TypeToken<List<TaskSession>>() {}.type

    private val _history = MutableStateFlow<List<TaskSession>>(loadHistory())
    val history: StateFlow<List<TaskSession>> = _history.asStateFlow()

    private fun loadHistory(): List<TaskSession> {
        val json = prefs.getString("sessions", null) ?: return emptyList()
        return try {
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveSession(session: TaskSession) {
        val current = _history.value.toMutableList()
        val index = current.indexOfFirst { it.sessionId == session.sessionId }
        if (index >= 0) {
            current[index] = session
        } else {
            current.add(0, session)
        }
        val trimmed = current.take(30)
        _history.value = trimmed
        prefs.edit().putString("sessions", gson.toJson(trimmed)).apply()
    }

    fun getSession(id: String): TaskSession? {
        return _history.value.firstOrNull { it.sessionId == id }
    }

    fun clearHistory() {
        prefs.edit().remove("sessions").apply()
        _history.value = emptyList()
    }
}
