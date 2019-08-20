package com.billwetter.haveyouheard.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.billwetter.haveyouheard.BR
import com.billwetter.haveyouheard.ui.common.viewmodel.BaseViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<T: BaseViewModel, B: ViewDataBinding>(private val viewModelClass: Class<T>, private val layoutId: Int): AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel : T
    lateinit var binding : B

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        binding.setVariable(BR.viewModel, viewModel)

        prepareView()
    }

    abstract fun prepareView()

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }
}