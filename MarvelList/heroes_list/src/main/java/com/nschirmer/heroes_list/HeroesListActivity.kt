package com.nschirmer.heroes_list

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.nschirmer.heroes_list.list.viewpager.HeroListAdapter
import com.nschirmer.heroes_list.list.viewpager.PageParallaxTransformer
import com.nschirmer.marvellist.util.BaseActivity
import com.nschirmer.request_api.requestapi.ConnectionListener
import com.nschirmer.response_objects.Character
import com.nschirmer.webservice.character.CharacterService
import kotlinx.android.synthetic.main.activity_heroes_list.*
import androidx.appcompat.app.AlertDialog




class HeroesListActivity : BaseActivity() {

    private val heroesList: MutableList<Character> = mutableListOf()
    private lateinit var listAdapter: HeroListAdapter
    private var offsetDownloadIndex = 0
    private var isLoadingCharacter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heroes_list)

        setPageAdapter()
        setViewPager()
        getMoreCharacters() // get the first set of characters
        showHowToUse()
    }


    private fun showHowToUse(){
        AlertDialog.Builder(this).create().apply {
            setTitle(R.string.greeting_title)
            setMessage(getString(R.string.greetings_content))
            setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.greetings_confirm))
            { dialog, which -> dialog.dismiss() }
            show()
        }
    }


    private fun setPageAdapter(){
        listAdapter = HeroListAdapter(supportFragmentManager, heroesList)
    }


    private fun setViewPager(){
        hero_view_pager.apply {
            setPageTransformer(false,
                PageParallaxTransformer()
            )
            adapter = listAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    checkIfNeedToGetMoreCharacters(position)
                }
            })
        }
    }


    private fun checkIfNeedToGetMoreCharacters(position: Int){
        if(!isLoadingCharacter && position == offsetDownloadIndex - (CharacterService.requestQtyLimit / 3)){
            getMoreCharacters()
        }
    }


    private fun getMoreCharacters(){
        isLoadingCharacter = true
        CharacterService(this).getAllCharacters(offsetDownloadIndex, object : ConnectionListener<Character> {
            override fun onSuccess(response: List<Character>) {
                heroesList.addAll(response)
                listAdapter.notifyDataSetChanged()
                offsetDownloadIndex += CharacterService.requestQtyLimit // update offset value
                isLoadingCharacter = false
                dismissProgress()
            }

            override fun onFail(error: String?) {
                isLoadingCharacter = false
                dismissProgress()
            }

            override fun noInternet() {
                isLoadingCharacter = false
                dismissProgress()
            }
        })
    }


    private fun dismissProgress(){
        if(hero_progress.visibility == View.VISIBLE) hero_progress.visibility = View.GONE
    }

}
