package com.life_personallifeintelligence.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

import com.life_personallifeintelligence.data.AppDatabase
import com.life_personallifeintelligence.data.MemoryEntity
import com.life_personallifeintelligence.model.Memory

import com.life_personallifeintelligence.ui.add.AddMemoryScreen
import com.life_personallifeintelligence.ui.add.EditMemoryScreen
import com.life_personallifeintelligence.ui.appearance.AppearanceScreen
import com.life_personallifeintelligence.ui.dataSources.DataSourcesScreen
import com.life_personallifeintelligence.ui.done.DoneScreen
import com.life_personallifeintelligence.ui.family.FamilyPermissionsScreen
import com.life_personallifeintelligence.ui.home.HomeScreen
import com.life_personallifeintelligence.ui.inbox.InboxScreen
import com.life_personallifeintelligence.ui.notifications.NotificationsScreen
import com.life_personallifeintelligence.ui.privacy.PrivacyScreen
import com.life_personallifeintelligence.ui.profile.ProfileScreen
import com.life_personallifeintelligence.ui.responsibility.ResponsibilityDetailScreen
import com.life_personallifeintelligence.ui.settings.SettingsScreen
import com.life_personallifeintelligence.ui.knowledge.WhatLifeKnowsScreen
import com.life_personallifeintelligence.ui.pause.PauseLifeScreen
import com.life_personallifeintelligence.ui.delete.DeleteMemoriesScreen

import kotlinx.coroutines.launch


@Composable
fun AppNavigation() {

    // ============================================================
    // DATABASE
    // ============================================================

    val context = LocalContext.current

    val database = remember {
        AppDatabase.getDatabase(context)
    }

    val scope = rememberCoroutineScope()


    // ============================================================
    // CURRENT SCREEN
    // ============================================================

    var currentScreen by remember {
        mutableStateOf("home")
    }


    // ============================================================
    // PREVIOUS SCREEN
    // ============================================================

    var previousScreen by remember {
        mutableStateOf("home")
    }


    // ============================================================
    // SELECTED MEMORY
    // ============================================================

    var selectedMemory by remember {
        mutableStateOf<Memory?>(null)
    }


    // ============================================================
    // MEMORY LIST
    // ============================================================

    var memories by remember {
        mutableStateOf<List<Memory>>(emptyList())
    }


    // ============================================================
    // REFRESH MEMORIES
    // ============================================================

    fun refreshMemories() {

        scope.launch {

            val savedMemories =
                database
                    .memoryDao()
                    .getAllMemories()

            memories = savedMemories.map { item ->

                Memory(
                    id = item.id,
                    text = item.text,
                    category = item.category,
                    priority = item.priority,
                    isDone = item.isDone
                )
            }
        }
    }


    // ============================================================
    // INITIAL LOAD
    // ============================================================

    LaunchedEffect(Unit) {
        refreshMemories()
    }


    // ============================================================
    // ANDROID BACK BUTTON
    // ============================================================

    BackHandler(
        enabled = currentScreen != "home"
    ) {

        when (currentScreen) {

            "settings" -> {
                currentScreen = "home"
            }

            "profile" -> {
                currentScreen = "settings"
            }

            "appearance" -> {
                currentScreen = "settings"
            }

            "notifications" -> {
                currentScreen = "settings"
            }

            "privacy" -> {
                currentScreen = "settings"
            }

            "data_sources" -> {
                currentScreen = "settings"
            }

            "what_life_knows" -> {
                currentScreen = "settings"
            }

            "family_responsibilities" -> {
                currentScreen = "settings"
            }

            "pause_life" -> {
                currentScreen = "settings"
            }

            "delete_memories" -> {
                currentScreen = "settings"
            }

            "responsibility_detail" -> {

                selectedMemory = null
                currentScreen = "inbox"
            }

            "inbox" -> {
                currentScreen = "home"
            }

            "done" -> {
                currentScreen = "home"
            }

            "add" -> {
                currentScreen = previousScreen
            }

            "edit" -> {

                selectedMemory = null
                currentScreen = previousScreen
            }

            else -> {
                currentScreen = "home"
            }
        }
    }


    // ============================================================
    // SCREEN ROUTING
    // ============================================================

    when (currentScreen) {


        // ========================================================
        // HOME
        // ========================================================

        "home" -> {

            HomeScreen(

                memories = memories,

                onAddMemoryClick = {

                    previousScreen = "home"
                    currentScreen = "add"
                },

                onMemoryDone = { memoryId, isDone ->

                    scope.launch {

                        database
                            .memoryDao()
                            .updateMemoryStatus(
                                memoryId,
                                isDone
                            )

                        refreshMemories()
                    }
                },

                onMemoryDelete = { memoryId ->

                    scope.launch {

                        database
                            .memoryDao()
                            .deleteMemory(
                                memoryId
                            )

                        refreshMemories()
                    }
                },

                onMemoryEdit = { memory ->

                    selectedMemory = memory
                    previousScreen = "home"
                    currentScreen = "edit"
                },

                onInboxClick = {

                    currentScreen = "inbox"
                },

                onDoneClick = {

                    currentScreen = "done"
                },

                onInsightsClick = {
                    // Future
                },

                onWaitingClick = {
                    // Future
                },

                onResponsibilityClick = {
                    // Future
                },

                onSettingsClick = {

                    previousScreen = "home"
                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // SETTINGS
        // ========================================================

        "settings" -> {

            SettingsScreen(

                onBackClick = {

                    currentScreen = "home"
                },


                // PROFILE
                onProfileClick = {

                    previousScreen = "settings"
                    currentScreen = "profile"
                },


                // NOTIFICATIONS
                onNotificationsClick = {

                    previousScreen = "settings"
                    currentScreen = "notifications"
                },


                // APPEARANCE
                onAppearanceClick = {

                    previousScreen = "settings"
                    currentScreen = "appearance"
                },


                // PRIVACY
                onPrivacyClick = {

                    previousScreen = "settings"
                    currentScreen = "privacy"
                },


                // DATA SOURCES
                onDataSourcesClick = {

                    previousScreen = "settings"
                    currentScreen = "data_sources"
                },


                // WHAT LIFE KNOWS
                onWhatLifeKnowsClick = {

                    previousScreen = "settings"
                    currentScreen = "what_life_knows"
                },


                // FAMILY RESPONSIBILITIES
                onFamilyPermissionsClick = {

                    previousScreen = "settings"
                    currentScreen =
                        "family_responsibilities"
                },


                // PAUSE LIFE
                onPauseLifeClick = {

                    previousScreen = "settings"
                    currentScreen = "pause_life"
                },


                // DELETE MEMORIES
                onDeleteMemoriesClick = {

                    previousScreen = "settings"
                    currentScreen = "delete_memories"
                }
            )
        }


        // ========================================================
        // PROFILE
        // ========================================================

        "profile" -> {

            ProfileScreen(

                onBackClick = {

                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // APPEARANCE
        // ========================================================

        "appearance" -> {

            AppearanceScreen(

                onBackClick = {

                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // NOTIFICATIONS
        // ========================================================

        "notifications" -> {

            NotificationsScreen(

                onBackClick = {

                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // PRIVACY
        // ========================================================

        "privacy" -> {

            PrivacyScreen(

                onBackClick = {

                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // DATA SOURCES
        // ========================================================

        "data_sources" -> {

            DataSourcesScreen(

                onBackClick = {

                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // WHAT LIFE KNOWS
        // ========================================================

        "what_life_knows" -> {

            WhatLifeKnowsScreen(

                onBackClick = {

                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // FAMILY RESPONSIBILITIES
        // ========================================================

        "family_responsibilities" -> {

            FamilyPermissionsScreen(

                onBackClick = {

                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // PAUSE LIFE
        // ========================================================

        "pause_life" -> {

            PauseLifeScreen(

                onBackClick = {

                    currentScreen = "settings"
                }
            )
        }


        // ========================================================
        // DELETE MEMORIES
        // ========================================================

        "delete_memories" -> {

            DeleteMemoriesScreen(

                onBackClick = {

                    currentScreen = "settings"
                },

                onDeleteAllClick = {

                    scope.launch {

                        memories.forEach { memory ->

                            database
                                .memoryDao()
                                .deleteMemory(
                                    memory.id
                                )
                        }

                        refreshMemories()

                        currentScreen = "settings"
                    }
                }
            )
        }


        // ========================================================
        // INBOX
        // ========================================================

        "inbox" -> {

            InboxScreen(

                memories = memories,

                onBackClick = {

                    currentScreen = "home"
                },

                onAddMemory = {

                    previousScreen = "inbox"
                    currentScreen = "add"
                },

                onMemoryClick = { memory ->

                    selectedMemory = memory

                    currentScreen =
                        "responsibility_detail"
                }
            )
        }


        // ========================================================
        // RESPONSIBILITY DETAIL
        // ========================================================

        "responsibility_detail" -> {

            selectedMemory?.let { memory ->

                ResponsibilityDetailScreen(

                    memory = memory,

                    onBackClick = {

                        selectedMemory = null
                        currentScreen = "inbox"
                    },

                    onComplete = {

                        scope.launch {

                            database
                                .memoryDao()
                                .updateMemoryStatus(
                                    memory.id,
                                    true
                                )

                            refreshMemories()

                            selectedMemory =
                                memory.copy(
                                    isDone = true
                                )
                        }
                    },

                    onTransfer = {
                        // Future
                    },

                    onWhyClick = {
                        // Future LIFE intelligence
                    },

                    onEvidenceClick = {
                        // Future
                    }
                )
            }
        }


        // ========================================================
        // DONE
        // ========================================================

        "done" -> {

            DoneScreen(

                memories = memories,

                onBackClick = {

                    currentScreen = "home"
                }
            )
        }


        // ========================================================
        // ADD MEMORY
        // ========================================================

        "add" -> {

            AddMemoryScreen(

                onSave = {
                        text,
                        category,
                        priority ->

                    scope.launch {

                        database
                            .memoryDao()
                            .insertMemory(

                                MemoryEntity(
                                    text = text,
                                    category = category,
                                    priority = priority
                                )
                            )

                        refreshMemories()

                        currentScreen =
                            previousScreen
                    }
                },

                onCancel = {

                    currentScreen =
                        previousScreen
                }
            )
        }


        // ========================================================
        // EDIT MEMORY
        // ========================================================

        "edit" -> {

            selectedMemory?.let { memory ->

                EditMemoryScreen(

                    memory = memory,

                    onSave = {
                            text,
                            category,
                            priority ->

                        scope.launch {

                            database
                                .memoryDao()
                                .updateMemory(
                                    memory.id,
                                    text,
                                    category,
                                    priority
                                )

                            refreshMemories()

                            selectedMemory = null

                            currentScreen =
                                previousScreen
                        }
                    },

                    onCancel = {

                        selectedMemory = null

                        currentScreen =
                            previousScreen
                    }
                )
            }
        }
    }
}