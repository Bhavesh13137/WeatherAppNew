package com.bhavesh.weatherappindianic.fragmentui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bhavesh.weatherappindianic.R
import android.view.LayoutInflater as LayoutInflater1

class TabOneFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater1, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.one_fragment, container, false
        )
    }
}