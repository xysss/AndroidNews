package com.xysss.androidnews.viewmodel.state

import com.xysss.androidnews.data.model.enums.TodoType
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.callback.databind.IntObservableField
import com.xysss.jetpackmvvm.callback.databind.StringObservableField
import com.xysss.jetpackmvvm.ext.launch

/**
 * Author:bysd-2
 * Time:2021/4/3013:57
 */

class TodoViewModel : BaseViewModel() {

    //标题
    var todoTitle = StringObservableField()

    //内容
    var todoContent =
        StringObservableField()

    //时间
    var todoTime = StringObservableField()

    //优先级
    var todoLeve =
        StringObservableField(TodoType.TodoType1.content)

    //优先级颜色
    var todoColor =
        IntObservableField(TodoType.TodoType1.color)

    fun xx(): Unit {
        launch({

        },{

        })
    }
}