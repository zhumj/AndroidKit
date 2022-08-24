package com.zhumj.androidkitproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zhumj.androidkitproject.fragment.Main2Fragment

/**
 * @Author Created by zhumj
 * @Date 2022/8/24 9:50
 * @Description 文件描述
 */
class Main2ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return Main2Fragment.newInstance(
            when (position) {
                0 -> {
                    "Main2 Fragment1"
                }
                1 -> {
                    "Main2 Fragment2"
                }
                else -> {
                    "Main2 Fragment3"
                }
            }
        )
    }
}