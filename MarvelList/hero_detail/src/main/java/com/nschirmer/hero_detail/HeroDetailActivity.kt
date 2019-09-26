package com.nschirmer.hero_detail

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nschirmer.hero_detail.comic_list.ComicListAdapter
import com.nschirmer.heroes_list.list.HeroBundle.Companion.TAG_HERO_BUNDLE
import com.nschirmer.marvellist.util.BaseActivity
import com.nschirmer.request_api.requestapi.ConnectionListener
import com.nschirmer.response_objects.Character
import com.nschirmer.response_objects.Comic
import com.nschirmer.webservice.comics.ComicsService
import kotlinx.android.synthetic.main.content_hero_detail.*
import kotlinx.android.synthetic.main.content_image_hero_detail.*

class HeroDetailActivity : BaseActivity() {

    private lateinit var character: Character
    private lateinit var comicAdapter: ComicListAdapter
    private val comicList: MutableList<Comic> = mutableListOf()
    private var offsetDownloadIndex = 0
    private var isLoadingComics = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)

        setToolbar()
        getCharacter()
        setView()
    }


    private fun setToolbar(){
        toolbar.apply {
            setSupportActionBar(this)
            setNavigationIcon(com.nschirmer.core_ui.R.drawable.ic_close)
            setNavigationOnClickListener { finish() }
            supportActionBar?.let {
                it.setDisplayShowTitleEnabled(false)
                it.setDisplayHomeAsUpEnabled(true)
            }
        }
    }


    private fun getCharacter(){
        getBundle().run {
            getString(TAG_HERO_BUNDLE).let {
                character = Gson().fromJson(it, Character::class.java)
            }
        }
    }


    private fun setView(){
        setHeroPhoto(character.thumbnail.fullUrl)
        setHeroName(character.name)
        setHeroDescription(character.description)
        if(character.comics.available > 0) setComics()
    }


    private fun setHeroPhoto(photoUrl: String){
        Uri.parse(photoUrl).let {
            hero_photo.setImageURI(it, this)
        }
    }


    private fun setHeroName(heroName: String){
        hero_name.text = heroName
    }


    private fun setHeroDescription(heroDescription: String){
        if(heroDescription.isNotEmpty()) hero_description.text = heroDescription
    }


    private fun setComics(){
        hero_no_comics.visibility = View.GONE
        getMoreComics()

        comicAdapter = ComicListAdapter(this, comicList)
        val gridLayoutManager = GridLayoutManager(this, 2)
        comics_list.apply {
            visibility = View.VISIBLE
            layoutManager = gridLayoutManager
            adapter = comicAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    checkIfNeedToGetMoreComics(gridLayoutManager.findLastVisibleItemPosition())
                }
            })
        }
    }


    private fun checkIfNeedToGetMoreComics(position: Int){
        if(!isLoadingComics && position > offsetDownloadIndex - (ComicsService.requestQtyLimit/2)){
            getMoreComics()
        }
    }


    private fun getMoreComics(){
        isLoading(true)
        ComicsService(this).getComics(character.id, offsetDownloadIndex,
            object : ConnectionListener<Comic> {
                override fun onSuccess(response: List<Comic>) {
                    comicList.addAll(response)
                    offsetDownloadIndex += ComicsService.requestQtyLimit // update offset value
                    comicAdapter.notifyDataSetChanged()
                    isLoading(false)
                }

                override fun onFail(error: String?) {
                    isLoading(false)
                }

                override fun noInternet() {
                    isLoading(false)
                }
            })
    }


    private fun isLoading(isLoading: Boolean){
        comic_progress.visibility = if(isLoading) View.VISIBLE else View.GONE
        isLoadingComics = isLoading
    }
}
