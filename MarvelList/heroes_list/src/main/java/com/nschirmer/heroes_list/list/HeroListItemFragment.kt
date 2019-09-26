package com.nschirmer.heroes_list.list

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.nschirmer.heroes_list.R
import com.nschirmer.heroes_list.list.HeroBundle.Companion.TAG_HERO_BUNDLE
import com.nschirmer.marvellist.util.BaseActivity
import com.nschirmer.marvellist.util.ModuleHelper
import com.nschirmer.response_objects.Character
import kotlinx.android.synthetic.main.fragment_hero_list_item.*


class HeroListItemFragment: Fragment() {

    private lateinit var character: Character

    companion object {
        @JvmStatic
        fun newInstance(character: Character) = HeroListItemFragment().apply {
            arguments = Bundle().apply {
                putString(TAG_HERO_BUNDLE, Gson().toJson(character))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hero_list_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCharacter()
        setView()
        setViewClickListener(view)
    }


    private fun setCharacter(){
        arguments?.run{
            getString(TAG_HERO_BUNDLE)?.run {
                character = Gson().fromJson(this, Character::class.java)
            }
        }
    }

    private fun setView(){
        setHeroName(character.name)
        setHeroPhoto(character.thumbnail.fullUrl)
    }


    private fun setHeroName(heroName: String){
        hero_name.text = heroName
    }


    private fun setHeroPhoto(photoUrl: String){
        Uri.parse(photoUrl).let {
            hero_photo.setImageURI(it, context)
        }
    }


    private fun setViewClickListener(view: View){
        view.setOnClickListener {
            Bundle().run {
                putString(TAG_HERO_BUNDLE, Gson().toJson(character)) // sending the character object to not download it again
                (activity as BaseActivity).openModule(ModuleHelper.HERO_DETAIL, this)
            }
        }
    }

}