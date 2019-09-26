package com.nschirmer.marvellist.util

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    companion object {
        const val TAG_BUNDLE = "bundle"
    }

    private val moduleManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }


    fun openModule(module: ModuleHelper, bundle: Bundle? = null){
        if(moduleManager.installedModules.contains(module.moduleName)){
            startModule(module, bundle)
        }
    }


    private fun startModule(module: ModuleHelper, bundle: Bundle?){
        Intent().setClassName(packageName, module.packageName).also { intent ->
            if(bundle != null) intent.putExtra(TAG_BUNDLE, bundle)
            startActivity(intent)
        }
    }


    fun getBundle(): Bundle {
        return intent.extras?.get(TAG_BUNDLE) as Bundle
    }

}