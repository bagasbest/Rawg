package com.project.rawg.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.project.rawg.R
import com.project.rawg.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var menuItemId: Int? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup NavController for your fragments.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val menuIntent = intent.getIntExtra(EXTRA_MENU, 0)
        if (menuIntent != 0) {
            binding.navView.selectedItemId = menuIntent
            navController.navigate(menuIntent)
        }

        // This ties the BottomNavigationView with the NavController so that
        // selecting a menu item will navigate to the corresponding destination.
        binding.navView.setupWithNavController(navController)

        binding.navView.setOnItemSelectedListener { item ->
            if (binding.navView.selectedItemId == item.itemId) {
                return@setOnItemSelectedListener true
            }

            when (item.itemId) {
                R.id.navigation_favorite -> {
                    // For favorite, install dynamic module and launch FavoriteActivity.
                    installFavoriteModule()
                    true
                }
                else -> {
                    menuItemId = item.itemId
                    // Define NavOptions that clear the back stack up to the start destination.
                    val navOptions = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(navController.graph.startDestinationId, inclusive = false)
                        .build()
                    navController.navigate(menuItemId!!, null, navOptions)
                    true
                }
            }
        }
    }


    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        // Check if the "favorite" module is already installed.
        val moduleName = "favorite"
        if (splitInstallManager.installedModules.contains(moduleName)) {
            launchFavoriteActivity()
        } else {
            // Request installation of the dynamic feature module.
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleName)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    // Once the module is installed, launch FavoriteActivity.
                    launchFavoriteActivity()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to install favorite module.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun launchFavoriteActivity() {
        // Replace with your actual fully qualified activity class name.
        val intent = Intent().apply {
            setClassName(packageName, "com.project.favorite.FavoriteActivity")
        }
        favoriteActivityLauncher.launch(intent)
    }

    private val favoriteActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.navView.selectedItemId = menuItemId ?: BASE_MENU_ID
        }
    }

    companion object {
        const val EXTRA_MENU = "menu"
        const val BASE_MENU_ID = 2131362165
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}