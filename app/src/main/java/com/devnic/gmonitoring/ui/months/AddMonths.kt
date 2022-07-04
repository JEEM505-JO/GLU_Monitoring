package com.devnic.gmonitoring.ui.months

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.database.DataBaseM
import com.devnic.gmonitoring.databinding.FragmentAddMonthsBinding
import com.devnic.gmonitoring.repository.RepositoryMonths
import com.google.android.material.snackbar.Snackbar
import java.util.*


class AddMonths : Fragment() {
    private lateinit var binding: FragmentAddMonthsBinding
    private lateinit var vMAddMonths: ViewModelAddMonths
    private lateinit var factory: AddMonthsFactory
    private val dataBaseM by lazy { DataBaseM.getDatabase(requireContext()) }
    private val repositoryMonths by lazy { RepositoryMonths(dataBaseM.daomonths()) }

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_months, container, false)
        factory = AddMonthsFactory(repositoryMonths)
        vMAddMonths = ViewModelProvider(this, factory)[ViewModelAddMonths::class.java]
        binding.lifecycleOwner = this
        binding.view = vMAddMonths

        var calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE))
        calendar.set(Calendar.HOUR_OF_DAY, 23) //not sure this is needed

        val endOfMonth: Long = calendar.timeInMillis
        //may need to reinitialize calendar, not sure
        //may need to reinitialize calendar, not sure
        calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        val startOfMonth: Long = calendar.timeInMillis
        binding.calendarViewMonths.maxDate = endOfMonth
        binding.calendarViewMonths.minDate = startOfMonth

        binding.calendarViewMonths.setOnDateChangeListener { _, year, month, day ->
            vMAddMonths.year.value = year
            vMAddMonths.months.value = month
            vMAddMonths.day.value = day
        }

        vMAddMonths.sms.observeForever {
            view?.let { it1 ->
                Snackbar.make(it1, it, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#0283C8")).setTextColor(Color.BLACK).show()
            }
        }
        vMAddMonths.boolean.observeForever {
            if (it) {
                findNavController().navigate(R.id.action_addMonths_to_months)
            }
        }

        return binding.root
    }

}