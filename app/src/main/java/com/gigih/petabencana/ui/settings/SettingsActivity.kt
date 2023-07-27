package com.gigih.petabencana.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.gigih.petabencana.R
import com.gigih.petabencana.utils.DarkMode

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            //TODO 11 : Update theme based on value in ListPreference
            val themePreference = findPreference<ListPreference>("key_dark_mode")
            themePreference?.setOnPreferenceChangeListener { _, newValue ->
                val darkModeValues = resources.getStringArray(R.array.dark_mode_value)
                when (newValue) {
                    darkModeValues[0] -> updateTheme(DarkMode.FOLLOW_SYSTEM.value)
                    darkModeValues[1] -> updateTheme(DarkMode.ON.value)
                    darkModeValues[2] -> updateTheme(DarkMode.OFF.value)
                    else -> updateTheme(DarkMode.FOLLOW_SYSTEM.value)
                }
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}