package com.xysss.androidnews.ui.fragment.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.init
import com.xysss.androidnews.app.ext.initClose
import com.xysss.androidnews.app.ext.setUiTheme
import com.xysss.androidnews.app.util.CacheUtil
import com.xysss.androidnews.app.util.SettingUtil
import com.xysss.androidnews.databinding.FragmentSearchBinding
import com.xysss.androidnews.ui.adapter.SearcHistoryAdapter
import com.xysss.androidnews.ui.adapter.SearcHotAdapter
import com.xysss.androidnews.viewmodel.request.RequestSearchViewModel
import com.xysss.androidnews.viewmodel.state.SearchViewModel
import com.xysss.jetpackmvvm.ext.nav
import com.xysss.jetpackmvvm.ext.navigateAction
import com.xysss.jetpackmvvm.ext.parseState
import com.xysss.jetpackmvvm.ext.util.toJson
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Author:bysd-2
 * Time:2021/4/3014:56
 */

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    private val historyAdapter: SearcHistoryAdapter by lazy { SearcHistoryAdapter(arrayListOf()) }

    private val hotAdapter: SearcHotAdapter by lazy { SearcHotAdapter(arrayListOf()) }

    private val requestSearchViewModel: RequestSearchViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_search

    override fun initView(savedInstanceState: Bundle?) {
        setMenu()
        appViewModel.appColor.value?.let { setUiTheme(it, search_text1, search_text2) }
        //?????????????????????Recyclerview
        search_historyRv.init(LinearLayoutManager(context), historyAdapter, false)
        //???????????????Recyclerview
        val layoutManager = FlexboxLayoutManager(context)
        //?????? ???????????????????????????????????????
        layoutManager.flexDirection = FlexDirection.ROW
        //?????????
        layoutManager.justifyContent = JustifyContent.FLEX_START
        search_hotRv.init(layoutManager, hotAdapter, false)

        historyAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val queryStr = historyAdapter.data[position]
                updateKey(queryStr)
                nav().navigateAction(R.id.action_searchFragment_to_searchResultFragment,
                    Bundle().apply {
                        putString("searchKey", queryStr)
                    }
                )
            }
            addChildClickViewIds(R.id.item_history_del)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_history_del -> {
                        requestSearchViewModel.historyData.value?.let {
                            it.removeAt(position)
                            requestSearchViewModel.historyData.value= it
                        }
                    }
                }
            }
        }

        hotAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val queryStr = hotAdapter.data[position].name
                updateKey(queryStr)
                nav().navigateAction(R.id.action_searchFragment_to_searchResultFragment,
                    Bundle().apply {
                        putString("searchKey", queryStr)
                    }
                )
            }
        }

        search_clear.setOnClickListener {
            activity?.let {
                MaterialDialog(it)
                    .cancelable(false)
                    .lifecycleOwner(this)
                    .show {
                        title(text = "????????????")
                        message(text = "????????????????")
                        negativeButton(text = "??????")
                        positiveButton(text = "??????") {
                            //??????
                            requestSearchViewModel.historyData.value = arrayListOf()
                        }
                        getActionButton(WhichButton.POSITIVE).updateTextColor(
                            SettingUtil.getColor(
                                it
                            )
                        )
                        getActionButton(WhichButton.NEGATIVE).updateTextColor(
                            SettingUtil.getColor(
                                it
                            )
                        )
                    }
            }
        }
    }

    override fun createObserver() {
        requestSearchViewModel.run {
            //????????????????????????
            hotData.observe(viewLifecycleOwner, Observer {resultState->
                parseState(resultState, {
                    hotAdapter.setList(it)
                })
            })
            //????????????????????????
            historyData.observe(viewLifecycleOwner, Observer {
                historyAdapter.data = it
                historyAdapter.notifyDataSetChanged()
                CacheUtil.setSearchHistoryData(it.toJson())

            })
        }
    }

    override fun lazyLoadData() {
        //???????????????????????????
        requestSearchViewModel.getHistoryData()
        //??????????????????
        requestSearchViewModel.getHotData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView
        searchView.run {
            maxWidth = Integer.MAX_VALUE
            onActionViewExpanded()
            queryHint = "?????????????????????"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                //searchview?????????
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //?????????????????? ???????????????????????????????????????????????????
                    query?.let { queryStr ->
                        updateKey(queryStr)
                        nav().navigateAction(R.id.action_searchFragment_to_searchResultFragment,
                            Bundle().apply {
                                putString("searchKey", queryStr)
                            }
                        )
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            isSubmitButtonEnabled = true //??????????????????????????????
            val field = javaClass.getDeclaredField("mGoButton")
            field.run {
                isAccessible = true
                val mGoButton = get(searchView) as ImageView
                mGoButton.setImageResource(R.drawable.ic_search)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    /**
     * ???????????????
     */
    fun updateKey(keyStr: String) {
        requestSearchViewModel.historyData.value?.let {
            if (it.contains(keyStr)) {
                //???????????????????????????????????? ??????
                it.remove(keyStr)
            } else if (it.size >= 10) {
                //???????????????size ???10?????????????????????????????????
                it.removeAt(it.size - 1)
            }
            //???????????????????????????
            it.add(0, keyStr)
            requestSearchViewModel.historyData.value = it
        }
    }

    override fun onResume() {
        super.onResume()
        //??????Fragment????????????????????????????????????Menu???????????????WebFragment ActionBar?????????????????????????????????ActionBar????????????bug
        setMenu()
    }

    private fun setMenu() {
        setHasOptionsMenu(true)
        toolbar.run {
            //??????menu ????????????
            mActivity.setSupportActionBar(this)
            initClose {
                nav().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity.setSupportActionBar(null)
    }

}