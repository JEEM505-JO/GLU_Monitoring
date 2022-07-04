package com.devnic.gmonitoring.ui.weeks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.database.DataBaseM
import com.devnic.gmonitoring.databinding.FragmentAddWeeksBinding
import com.devnic.gmonitoring.repository.RepositoryWeeks
import com.devnic.gmonitoring.util.CalendarClass
import java.nio.BufferUnderflowException
import java.util.*


class AddWeeks : Fragment() {
    private lateinit var binding: FragmentAddWeeksBinding
    private val dataBaseM by lazy { DataBaseM.getDatabase(requireContext()) }
    private val repositoryWeeks by lazy { RepositoryWeeks(dataBaseM.daoweeks()) }
    private lateinit var factory: AddWeeksFactory
    private lateinit var vMAddWeeks: ViewModelAddWeeks
    private var month: Int = 0
    private var day: Int = 0
    private var idmon: Long = 0L
    private val bundle: Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            month = it.getInt("month")
            day = it.getInt("day")
            idmon = it.getLong("idmonth")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_weeks, container, false)
        binding.lifecycleOwner = this
        factory = AddWeeksFactory(repositoryWeeks)
        vMAddWeeks = ViewModelProvider(this, factory)[ViewModelAddWeeks::class.java]
        binding.view = vMAddWeeks


        vMAddWeeks.idmonth.postValue(idmon)
        bundle.putLong("idmon",idmon)
        bundle.putInt("day",day)
        bundle.putInt("idmon",month)

        vMAddWeeks.sms.observeForever {
            it?.let {
                dialog(it)
            }
        }

        vMAddWeeks.boolean.observeForever {
            it?.let {
                if (it) {
                    findNavController().navigate(R.id.action_addWeeks_to_weeks,bundle)
                }
            }
        }

        val calendarstart: Calendar = CalendarClass().getdaystart(month, day)
        val calendarend: Calendar = CalendarClass().getdayend(month)

        val end: Long = calendarend.timeInMillis

        val startOfMonth: Long = calendarstart.timeInMillis

        binding.idinitweek.setOnDateChangedListener { _, _, _, initday ->
            vMAddWeeks.initday.value = initday
        }
        binding.idfinweek.setOnDateChangedListener { _, _, _, finday ->
            vMAddWeeks.finday.value = finday
        }

        //estableciendo inicio y fin
        binding.idinitweek.minDate = startOfMonth
        binding.idinitweek.maxDate = end
        binding.idfinweek.minDate = startOfMonth
        binding.idfinweek.maxDate = end

        return binding.root
    }

    fun dialog(sms: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("ERROR DE INGRESO")
        builder.setIcon(R.drawable.week)
        builder.setMessage(
            sms
        )
        builder.setPositiveButton("Continuar") { _, _ ->
            builder.create().dismiss()
        }
        builder.create().show()
    }
}