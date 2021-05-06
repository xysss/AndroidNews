package com.xysss.androidnews.ui.fragment.todo

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.eventViewModel
import com.xysss.androidnews.app.ext.initClose
import com.xysss.androidnews.app.ext.showMessage
import com.xysss.androidnews.app.util.DatetimeUtil
import com.xysss.androidnews.app.util.SettingUtil
import com.xysss.androidnews.app.weight.customview.PriorityDialog
import com.xysss.androidnews.data.model.bean.TodoResponse
import com.xysss.androidnews.data.model.enums.TodoType
import com.xysss.androidnews.databinding.FragmentAddtodoBinding
import com.xysss.androidnews.viewmodel.request.RequestTodoViewModel
import com.xysss.androidnews.viewmodel.state.TodoViewModel
import com.xysss.jetpackmvvm.ext.nav
import com.xysss.jetpackmvvm.ext.util.notNull
import kotlinx.android.synthetic.main.fragment_addtodo.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.util.*

/**
 * Author:bysd-2
 * Time:2021/4/3014:57
 */

class AddTodoFragment : BaseFragment<TodoViewModel, FragmentAddtodoBinding>() {

    private var todoResponse: TodoResponse? = null

    //请求数据ViewModel
    private val requestViewModel: RequestTodoViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_addtodo

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        arguments?.let {
            todoResponse = it.getParcelable("todo")
            todoResponse?.let { todo ->
                mViewModel.todoTitle.set(todo.title)
                mViewModel.todoContent.set(todo.content)
                mViewModel.todoTime.set(todo.dateStr)
                mViewModel.todoLeve.set(TodoType.byType(todo.priority).content)
                mViewModel.todoColor.set(TodoType.byType(todo.priority).color)
            }
        }
        toolbar.initClose(if (todoResponse == null) "添加TODO" else "修改TODO") {
            nav().navigateUp()
        }
        appViewModel.appColor.value?.let { SettingUtil.setShapColor(addtodoSubmit, it) }
    }

    override fun createObserver() {
        requestViewModel.updateDataState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                //添加TODO成功 返回并发送消息回调
                nav().navigateUp()
                eventViewModel.todoEvent.setValue(false)
            } else {
                showMessage(it.errorMsg)
            }
        })
    }

    inner class ProxyClick {
        /** 选择时间*/
        fun todoTime() {
            activity?.let {
                MaterialDialog(it)
                    .lifecycleOwner(this@AddTodoFragment).show {
                        cornerRadius(0f)
                        datePicker(minDate = Calendar.getInstance()) { dialog, date ->
                            mViewModel.todoTime.set(
                                DatetimeUtil.formatDate(
                                    date.time,
                                    DatetimeUtil.DATE_PATTERN
                                )
                            )
                        }
                    }
            }
        }

        /**选择类型*/
        fun todoType() {
            activity?.let {
                PriorityDialog(it, TodoType.byValue(mViewModel.todoLeve.get()).type).apply {
                    setPriorityInterface(object : PriorityDialog.PriorityInterface {
                        override fun onSelect(type: TodoType) {
                            mViewModel.todoLeve.set(type.content)
                            mViewModel.todoColor.set(type.color)
                        }
                    })
                }.show()
            }
        }

        /**提交*/
        fun submit() {
            when {
                mViewModel.todoTitle.get().isEmpty() -> {
                    showMessage("请填写标题")
                }
                mViewModel.todoContent.get().isEmpty() -> {
                    showMessage("请填写内容")
                }
                mViewModel.todoTime.get().isEmpty() -> {
                    showMessage("请选择预计完成时间")
                }
                else -> {
                    todoResponse.notNull({
                        showMessage("确认提交编辑吗？", positiveButtonText = "提交", positiveAction = {
                            requestViewModel.updateTodo(
                                it.id,
                                mViewModel.todoTitle.get(),
                                mViewModel.todoContent.get(),
                                mViewModel.todoTime.get(),
                                TodoType.byValue(mViewModel.todoLeve.get()).type
                            )
                        }, negativeButtonText = "取消")
                    }, {
                        showMessage("确认添加吗？", positiveButtonText = "添加", positiveAction = {
                            requestViewModel.addTodo(
                                mViewModel.todoTitle.get(),
                                mViewModel.todoContent.get(),
                                mViewModel.todoTime.get(),
                                TodoType.byValue(mViewModel.todoLeve.get()).type
                            )
                        }, negativeButtonText = "取消")
                    })
                }
            }
        }
    }
}