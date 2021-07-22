package com.xysss.androidnews.ui.fragment.demo.room_db

import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.xysss.androidnews.R
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.databinding.FragmentRoom1Binding
import kotlinx.android.synthetic.main.fragment_room1.*

/**
 * Create Date：2020/01/01
 * 实现Room数据的基本操作
 * 王志强
 */
class RoomSampleFragment : BaseFragment<RoomSampleViewModel,FragmentRoom1Binding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_room1
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.allWordsLive.observe(this, Observer { words ->
            val text = StringBuilder()
            for (i in words.indices) {
                val word = words[i]
                text.append(word.id).append(":").append(word.word).append("=").append(word.chineseMeaning).append("\n")
            }
            textView.text = text.toString()
        })
        buttonInsert.setOnClickListener {
            val word1 = Word("Hello", "你好！")
            mViewModel.insertWords(word1)
        }
        buttonClear.setOnClickListener { mViewModel.deleteAllWords() }
        buttonUpdate.setOnClickListener {
            ToastUtils.showShort("更新 id=1 的数据...")
            val word = Word("update", "更新!")
            word.id = 1
            mViewModel.updateWords(word)
        }
        buttonDelete.setOnClickListener {
            ToastUtils.showShort("删除 id=1 的数据...")
            val word = Word("update", "更新!")
            word.id = 1
            mViewModel.deleteWords(word)
        }
    }


}