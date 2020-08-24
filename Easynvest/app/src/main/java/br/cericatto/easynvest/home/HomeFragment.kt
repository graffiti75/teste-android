package br.cericatto.easynvest.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.cericatto.easynvest.R
import br.cericatto.easynvest.databinding.FragmentHomeBinding
import br.cericatto.easynvest.utils.DatePickerFragment
import br.cericatto.easynvest.utils.dateIsInFuture
import br.cericatto.easynvest.utils.formatMaturityDate
import br.cericatto.easynvest.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    companion object {
        private const val DELAY: Long = 1250
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    //--------------------------------------------------
    // Fragment Life Cycle
    //--------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment.
        binding.lifecycleOwner = this

        // Giving the binding access to the HomeViewModel.
        binding.viewModel = viewModel
        setEditTexts(binding)
        binding.investmentEditText.requestFocus()

        // Live Data.
        observeInvestmentEditTextIsValid(binding)
        observeDateEditTextIsValid(binding)
        observeCdiEditTextIsValid(binding)
        observeNavigateToSelectedProperty()

        return binding.root
    }

    //--------------------------------------------------
    // Live Data Methods
    //--------------------------------------------------

    private fun observeInvestmentEditTextIsValid(binding: FragmentHomeBinding) {
        val investmentEditText = binding.investmentEditText
        val dateEditText = binding.maturityDateEditText
        viewModel.investmentEditTextIsValid.observe(viewLifecycleOwner, Observer {
            val starting = viewModel.startup.value!!
            if (!starting)
                if (!it) investmentEditText.error = getString(R.string.invalid_investment)
                else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        openDatePicker(dateEditText)
                    }, DELAY)
                }
        })
    }

    private fun observeDateEditTextIsValid(binding: FragmentHomeBinding) {
        val context = activity!!
        val dateEditText = binding.maturityDateEditText
        viewModel.dateEditTextIsValid.observe(viewLifecycleOwner, Observer {
            val starting = viewModel.startup.value!!
            if (!starting) {
                if (!it) {
                    Snackbar.make(
                        context.findViewById(android.R.id.content),
                        getString(R.string.invalid_maturity_date),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    dateEditText.error = getString(R.string.invalid_maturity_date)
                } else dateEditText.error = null
            }
        })
    }

    private fun observeCdiEditTextIsValid(binding: FragmentHomeBinding) {
        val cdiEditText = binding.cdiEditText
        viewModel.cdiEditTextIsValid.observe(viewLifecycleOwner, Observer {
            val starting = viewModel.startup.value!!
            if (!starting)
                if (!it) cdiEditText.error = getString(R.string.invalid_cdi)
        })
    }

    private fun observeNavigateToSelectedProperty() {
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(HomeFragmentDirections.actionShowResult(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
    }

    //--------------------------------------------------
    // UI Methods
    //--------------------------------------------------

    private fun setEditTexts(binding: FragmentHomeBinding) {
        investmentEditTextonFocusChangeListener(binding)
        dateEditTextListeners(binding)
        cdiEdiTextOnEditorActionListener(binding)
    }

    private fun investmentEditTextonFocusChangeListener(binding: FragmentHomeBinding) {
        val investmentEditText = binding.investmentEditText
        val context = activity!!
        investmentEditText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                viewModel.updateStartup()
                val text = investmentEditText.text.toString()
                val valid =  (text.isNotEmpty()) && (text.toDouble() > 0)
                viewModel.updateInvestmentEditText(valid, text.toDouble())
                context.hideKeyboard(view!!)
            }
        }
    }

    private fun dateEditTextListeners(binding: FragmentHomeBinding) {
        val dateEditText = binding.maturityDateEditText
        val cdiEdiText = binding.cdiEditText
        dateEditText.setOnClickListener {
            openDatePicker(dateEditText)
        }

        dateEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.updateStartup()
                val text = dateEditText.text.toString()
                val valid = text.isNotEmpty() && viewModel.getTimeMillis().dateIsInFuture()
                viewModel.updateDateTextView(valid, text.formatMaturityDate())
                cdiEdiText.requestFocus()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int ) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int ) {}
        })
    }

    private fun cdiEdiTextOnEditorActionListener(binding: FragmentHomeBinding) {
        val cdiEdiText = binding.cdiEditText
        cdiEdiText.setOnEditorActionListener { view, actionId, event ->
            if (EditorInfo.IME_ACTION_DONE == actionId) {
                viewModel.updateStartup()
                val text = cdiEdiText.text.toString()
                val valid =  (text.isNotEmpty()) && (text.toDouble() > 0)
                viewModel.updateCdiEditText(valid, text.toDouble())
            }
            false
        }
    }

    private fun openDatePicker(dateEditText: EditText) {
        val newFragment: DialogFragment = DatePickerFragment(viewModel, dateEditText)
        newFragment.show(fragmentManager!!, "DatePicker")
    }
}