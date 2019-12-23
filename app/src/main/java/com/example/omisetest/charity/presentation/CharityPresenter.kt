package com.example.omisetest.charity.presentation

import android.app.Activity
import android.support.constraint.ConstraintLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.example.omisetest.Configuration
import com.example.omisetest.Response
import com.example.omisetest.domain.CharityDetails
import com.example.omisetest.presentation.RecyclerViewListAdapter
import com.example.omisetest.presentation.RefreshableInteractor
import com.example.omisetest.presentation.charity.presentation.CharityViewModel
import com.example.omisetest.api.ConvertFromDomain.convert

class CharityPresenter(
    context: Activity,
    private val emptyView: ConstraintLayout,
    private val refreshContainer: SwipeRefreshLayout,
    recyclerView: RecyclerView,
    private var loader: ProgressBar,
    private var adapter: RecyclerViewListAdapter<CharityViewModel.CharityDetails>
) : RefreshableInteractor(context, refreshContainer) {

    private val showCharityList = Configuration.deps.useCase.charity.charitiesList

    init {
        recyclerView.adapter = adapter
        recyclerView.adapter.notifyDataSetChanged()
        reloadData()
    }

    fun onRefresh() {
        reloadData()
    }

    fun reloadData() = refresh { pushViewModel(fetchViewModel()) }

    private fun pushViewModel(vm: CharityViewModel) = ui {
        adapter.list = vm.data
        loader.visibility = View.GONE
        when (adapter.list.isEmpty()) {
            false -> {
                refreshContainer.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
            }
            true -> {
                refreshContainer.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }
        }
    }

    private fun fetchViewModel(): CharityViewModel = convert(showCharityList.request())

    companion object {
        fun convert(it: Response<List<CharityDetails>>) = when (it) {
            is Response.Success -> CharityViewModel(it.body.map { model -> convert(model) })
            is Response.Failure, is Response.Error -> CharityViewModel(emptyList())
        }
    }
}
