package br.cericatto.easynvest.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.cericatto.easynvest.DatePickerFragment
import br.cericatto.easynvest.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    /**
     * Lazily initialize our [HomeViewModel].
     */
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the HomeFragment
     * to enable Data Binding to observe LiveData.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding.simulateButton.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionShowResult())
        }
        setEditTexts(binding)

        return binding.root
    }

    private fun setEditTexts(binding: FragmentHomeBinding) {
        val dateTextView = binding.investmentMaturityDateEditText
        dateTextView.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment(it as TextView)
            newFragment.show(fragmentManager!!, "DatePicker")
        }
    }
}