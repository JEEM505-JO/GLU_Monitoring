package com.devnic.gmonitoring.ui.weeks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.database.DataBaseM
import com.devnic.gmonitoring.databinding.FragmentWeeksBinding
import com.devnic.gmonitoring.repository.RepositoryWeeks
import com.devnic.gmonitoring.util.SharedPreferences


class Weeks : Fragment() {
    private val dataBaseM by lazy { DataBaseM.getDatabase(requireContext()) }
    private val repositoryWeeks by lazy { RepositoryWeeks(dataBaseM.daoweeks()) }
    private lateinit var factory: WeeksFactory
    private lateinit var vMWeeks: ViewModelWeeks
    private lateinit var binding: FragmentWeeksBinding
    private lateinit var adapterWeeks: AdapterViewWeeks
    private lateinit var shared: SharedPreferences

    private val arg: WeeksArgs by navArgs()
    private var id: Long = 0L
    private var month: Int = 0
    private var day: Int = 0

    private var bun: Bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weeks, container, false)
        factory = WeeksFactory(repositoryWeeks)
        vMWeeks = ViewModelProvider(this, factory)[ViewModelWeeks::class.java]
        val rvweek = binding.rvweeks
        rvweek.layoutManager = LinearLayoutManager(context)

        //captando valor de sharedpreferences
        shared = SharedPreferences(requireContext())
        day = shared.getday()
        month = shared.getmonth()
        id = shared.getIDMonth().toLong()


        adapterWeeks = AdapterViewWeeks {
            findNavController().navigate(WeeksDirections.actionWeeksToDayMonth(it))
        }

        arg.monthmodel.let { arg ->
            if (arg == null) {
                vMWeeks.getlist(id).observeForever {
                    it?.let { list ->
                        adapterWeeks.submitList(list)
                    }
                }
            } else {
                vMWeeks.getlist(arg.idmonths).observeForever {
                    it?.let { list ->
                        adapterWeeks.submitList(list)
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_weeks_to_months)
        }
        adapterWeeks.notifyDataSetChanged()
        rvweek.adapter = adapterWeeks

        binding.lifecycleOwner = this
        binding.addmonths.setOnClickListener {
            arg.monthmodel.let {
                if (it == null) {
                    bun.putInt("month", month)
                    bun.putInt("day", day)
                    bun.putLong("idmonth", id)
                } else {
                    bun.putInt("month", it.months)
                    bun.putInt("day", it.day)
                    bun.putLong("idmonth", it.idmonths)
                }
            }
            findNavController().navigate(R.id.action_weeks_to_addWeeks, bun)
        }
        return binding.root
    }


}