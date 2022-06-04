package com.example.bankApplication_kotlin.viewModel

import android.app.Application
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bankApplication_kotlin.R
import com.example.bankApplication_kotlin.api.HomeAPI
import com.example.bankApplication_kotlin.api.model.AddressModel
import com.example.bankApplication_kotlin.api.model.BannerModel
import com.example.bankApplication_kotlin.api.model.HomeNaviMenu
import com.example.bankApplication_kotlin.event.Event
import com.example.bankApplication_kotlin.sharedPreference.PreferenceApplication
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _getBannerEvent = MutableLiveData<Event<Boolean>>()
    private val _currentFragment = MutableLiveData(HomeNaviMenu.HomeFragment)
    private val _homeCurMoney = MutableLiveData<String>()
    private val _userName = MutableLiveData<String>()
    val getBannerEvent : LiveData<Event<Boolean>>
        get() = _getBannerEvent
    val userName : LiveData<String>
        get() = _userName
    val homeCurMoney : LiveData<String>
        get() = _homeCurMoney
    var eventBanner = ArrayList<String>()
    var financialBanner = ArrayList<String>()
    val currentFragment : LiveData<HomeNaviMenu>
        get() = _currentFragment

    val onItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            val fragment = getFragment(item.itemId)
            changeCurrentFragment(fragment)
            true
        }
    init {
        _homeCurMoney.value = PreferenceApplication.prefs.loginGetString("money","")
        _userName.value = PreferenceApplication.prefs.loginGetString("Name","")
    }
    fun getBannerInfo() {
        val api = HomeAPI.create()
        api.getBanner().enqueue(object : Callback<List<BannerModel>>{
            override fun onResponse(call: Call<List<BannerModel>>, response: Response<List<BannerModel>>) {
                if(response.isSuccessful){
                    for(info in response.body()!!){
                        if (info.kinds == "event")
                            eventBanner.add(info.bannerRoute)
                        else
                            financialBanner.add(info.bannerRoute)
                    }
                    _getBannerEvent.value = Event(true)
                }
            }
            override fun onFailure(call: Call<List<BannerModel>>, t: Throwable) {
                Log.d("getBannerFail", t.message.toString())
            }
        })
    }
    private fun getFragment(menu_id: Int) : HomeNaviMenu
        = when(menu_id){
            R.id.nav_home -> HomeNaviMenu.HomeFragment
            R.id.nav_financial_products -> HomeNaviMenu.FinancialFragment
            R.id.nav_myBank -> HomeNaviMenu.MyBankFragment
            R.id.nav_myAsset -> HomeNaviMenu.MyAssetFragment
            else -> throw IllegalArgumentException("not found menu item id")
        }
    private fun changeCurrentFragment(fragment : HomeNaviMenu){
        if (_currentFragment.value == fragment)
            return
        else
            _currentFragment.value = fragment
    }
}