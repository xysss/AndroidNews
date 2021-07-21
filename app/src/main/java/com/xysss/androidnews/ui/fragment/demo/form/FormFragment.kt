package com.xysss.androidnews.ui.fragment.demo.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.xysss.androidnews.R
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.databinding.FragmentFormTempBinding
import java.util.*

/**
 * Author:bysd-2
 * Time:2021/7/1917:20
 */
class FormFragment : BaseFragment<FormViewModel,FragmentFormTempBinding>() {
    private lateinit var entity: FormEntity
    override fun layoutId(): Int {
        return R.layout.fragment_form_temp
    }

    override fun initData() {
        //获取列表传入的实体
        val mBundle = arguments
        if (mBundle != null) {
            entity = mBundle.getSerializable("entity") as FormEntity
        }
        mDatabind.presenter = Presenter()
        mDatabind.switchId.isChecked = entity.marry
        mViewModel.entityLiveData.value = entity
        //通过binding拿到toolbar控件, 设置给Activity
        (activity as AppCompatActivity?)?.setSupportActionBar(mDatabind.title.toolbar)
        //ID不为空是修改
        mDatabind.title.tvTitle.text = "表单编辑"
        mDatabind.title.ivRight.setOnClickListener { ToastUtils.showShort("点击了更多") }
        mDatabind.title.ivBack.setOnClickListener{ NavHostFragment.findNavController(this@FormFragment).navigateUp() }

        mDatabind.switchId.setOnCheckedChangeListener { buttonView, isChecked -> //是否已婚Switch点状态改变回调
            ToastUtils.showShort("婚姻状态::$isChecked")
            val value = mViewModel.entityLiveData.value
            value?.marry= isChecked
            mViewModel.entityLiveData.setValue(value)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    inner class Presenter {
        fun commitClick() {
            Toast.makeText(activity, "触发提交按钮", Toast.LENGTH_SHORT).show()
            val submitJson = Gson().toJson(mViewModel.entityLiveData.value)
//            MaterialDialogUtils.Companion.showBasicDialog(context, "提交的json实体数据：\r\n$submitJson").show()
        }

        fun showDateDialog() {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = context?.let {
                DatePickerDialog(it,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val value = mViewModel.entityLiveData.value
                        //设置数据到实体中，自动刷新界面
                        value?.bir = year.toString() + "年" + (month + 1) + "月" + dayOfMonth + "日"
                        mViewModel.entityLiveData.setValue(value)
                    }, year, month, day)
            }
            datePickerDialog?.setMessage("生日选择")
            datePickerDialog?.show()
        }
    }
}