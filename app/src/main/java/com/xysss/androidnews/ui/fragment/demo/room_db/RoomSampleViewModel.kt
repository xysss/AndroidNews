package com.xysss.androidnews.ui.fragment.demo.room_db

import androidx.lifecycle.LiveData
import com.xysss.jetpackmvvm.base.appContext
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel


/**
 * Create Date：2020/01/01
 * 实现Room数据的基本操作
 * 王志强
 */
class RoomSampleViewModel : BaseViewModel() {
    private val wordRepository: WordRepository = WordRepository(appContext)
    val allWordsLive: LiveData<List<Word>>
        get() = wordRepository.listLiveData

    fun insertWords(vararg words: Word?) {
        wordRepository.insertWords(*words)
    }

    fun updateWords(vararg words: Word?) {
        wordRepository.updateWords(*words)
    }

    fun deleteWords(vararg words: Word?) {
        wordRepository.deleteWords(*words)
    }

    fun deleteAllWords() {
        wordRepository.deleteAllWords()
    }

}