package com.nschirmer.marvellist

import android.os.Bundle
import com.nschirmer.marvellist.util.BaseActivity
import com.nschirmer.marvellist.util.ModuleHelper

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openModule(ModuleHelper.HEROES_LIST)
        finish()
    }

}
