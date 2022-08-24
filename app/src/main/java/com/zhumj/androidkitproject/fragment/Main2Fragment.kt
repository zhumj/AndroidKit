package com.zhumj.androidkitproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhumj.androidkit.base.BaseFragment
import com.zhumj.androidkitproject.databinding.FragmentMain2Binding
import com.zhumj.androidkitproject.mvp.contract.Main2F1Contract
import com.zhumj.androidkitproject.mvp.presenter.Main2F1Presenter

private const val ARG_PARAM = "param"

class Main2Fragment : BaseFragment<FragmentMain2Binding, Main2F1Presenter>(), Main2F1Contract.View {
    private var param: String = "Main2 Fragment1"

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMain2Binding {
        return FragmentMain2Binding.inflate(inflater, container, false)
    }

    override fun obtainPresenter(): Main2F1Presenter = Main2F1Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM) ?: "Main2 Fragment1"
        } ?: tag.let {
            param = it ?: "Main2 Fragment1"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter?.tag = param
        mViewBinding.tvText.post {
            mViewBinding.tvText.text = param
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            Main2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}