package com.nschirmer.marvellist.util


/**
 *  Class dedicated to be an abstraction of the module calling name and packaging.
 * @param moduleName is the module name from the specific module. Normally appear on top
 * in the project tree.
 * @param packageName is the package name from the activity that you want to call\
 * of that specific module. Unfortunately Google do not fixed this hardcoded solution.
 * **/
enum class ModuleHelper(val moduleName: String, val packageName: String) {

    HEROES_LIST("heroes_list", "com.nschirmer.heroes_list.HeroesListActivity"),
    HERO_DETAIL("hero_detail", "com.nschirmer.hero_detail.HeroDetailActivity")

}