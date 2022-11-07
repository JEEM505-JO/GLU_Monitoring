package com.devnic.gmonitoring.ui.months

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.database.DataBaseM
import com.devnic.gmonitoring.databinding.FragmentMonthsBinding
import com.devnic.gmonitoring.repository.RepositoryMonths
import com.devnic.gmonitoring.util.SharedPreferences
import com.jjoe64.graphview.ValueDependentColor
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlin.math.abs


class Months : Fragment() {
    private lateinit var binding: FragmentMonthsBinding
    private lateinit var vMMonths: ViewModelMonths
    private lateinit var factory: MonthsFactory
    private lateinit var adapterMonths: AdapterViewMonths
    private val dataBaseM by lazy { DataBaseM.getDatabase(requireContext()) }
    private val repositoryMonths by lazy { RepositoryMonths(dataBaseM.daomonths()) }
    private lateinit var initShared: SharedPreferences


    private var barr = BarGraphSeries(
        arrayOf(
            DataPoint(0.0, 4.0),
            DataPoint(2.0, 6.0),
            DataPoint(4.0, 3.0),
            DataPoint(5.3, 6.5),
            DataPoint(6.0, 5.6)
        )
    )

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_months, container, false)
        factory = MonthsFactory(repositoryMonths)
        vMMonths = ViewModelProvider(this, factory)[ViewModelMonths::class.java]
        binding.lifecycleOwner = this
        initShared = SharedPreferences(requireContext())


        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }

        adapterMonths = AdapterViewMonths {
            findNavController().navigate(MonthsDirections.actionMonthsToWeeks(it))
            initShared.deleteshared()
            initShared.saveIDMonth(it.idmonths)
            initShared.saveday(it.day)
            initShared.savemonth(it.months)
        }

        vMMonths.getmonths.observe(viewLifecycleOwner) { list ->
            list.let { adapterMonths.submitList(it) }
        }


        adapterMonths.notifyDataSetChanged()


        val rvmonth = binding.rvmonths
        rvmonth.layoutManager = LinearLayoutManager(context)
        rvmonth.adapter = adapterMonths

        barr.spacing = 50
        barr.dataWidth = 1.0
        binding.monthGrap.addSeries(barr)
        binding.monthGrap.title = "Medicion de glucosa"

        binding.addmonths.setOnClickListener {
            findNavController().navigate(R.id.action_months_to_addMonths)
        }

        barr.valueDependentColor = ValueDependentColor { data ->
            Color.rgb(
                data.x.toInt() * 255 / 4,
                abs(data.y * 255 / 6).toInt(),
                100
            )
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        vMMonths.getmonths.observe(viewLifecycleOwner) { list ->
            list.let { adapterMonths.submitList(it) }
        }
        binding.rvmonths.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMonths
        }
    }
}