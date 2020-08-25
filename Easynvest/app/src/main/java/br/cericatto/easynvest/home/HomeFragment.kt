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
import br.cericatto.easynvest.utils.*
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    companion object {
        private const val ANIMATION_DELAY: Long = 500
        private const val PICKER_DELAY: Long = 1000
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private var mMustFormatEditText = true

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
        binding.howMuchWouldYouLikeToApplyEditText.requestFocus()

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
        val investmentEditText = binding.howMuchWouldYouLikeToApplyEditText
        val dateEditText = binding.investmentMaturityDateEditText
        var emptyDateEditText = dateEditText.text.toString().isEmpty()
        viewModel.investmentEditTextIsValid.observe(viewLifecycleOwner, Observer {
            val starting = viewModel.startup.value!!
            if (!starting) {
                if (!it) investmentEditText.error = getString(R.string.invalid_investment)
                emptyDateEditText = dateEditText.text.toString().isEmpty()
                if (emptyDateEditText) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        initViewAnimation(
                            dateEditText,
                            dateEditText.context.anim(R.anim.zoom_in_95)
                        )
                        Handler(Looper.getMainLooper()).postDelayed({
                            openDatePicker(dateEditText)
                        }, PICKER_DELAY)
                    }, ANIMATION_DELAY)
                }
            }
        })
    }

    private fun observeDateEditTextIsValid(binding: FragmentHomeBinding) {
        val context = activity!!
        val dateEditText = binding.investmentMaturityDateEditText
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
        val cdiEditText = binding.whatIsCdiPercentageEditText
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
        investmentEditTextListeners(binding)
        dateEditTextListeners(binding)
        cdiEditTextListeners(binding)
    }

    private fun investmentEditTextListeners(binding: FragmentHomeBinding) {
        val investmentEditText = binding.howMuchWouldYouLikeToApplyEditText
        val context = activity!!
        investmentEditText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val text = investmentEditText.text.toString()
                val invalidText = text.isEmpty() || (text == ".")
                if (invalidText) {
                    investmentEditText.setText("")
                    viewModel.updateInvestmentEditText(false, 0.0)
                } else {
                    viewModel.updateStartup()
                    val valid = (text.isNotEmpty()) && (text.toDouble() > 0)
                    viewModel.updateInvestmentEditText(valid, text.toDouble())
                    investmentEditText.setText(text.toDouble().doubleToCurrency())
                    context.hideKeyboard(view!!)
                }
            } else {
                val formattedText = investmentEditText.text.toString().cleanCurrencyCharacters()
                investmentEditText.setText(formattedText)
            }
        }
    }

    private fun dateEditTextListeners(binding: FragmentHomeBinding) {
        val dateEditText = binding.investmentMaturityDateEditText
        val cdiEditText = binding.whatIsCdiPercentageEditText
        dateEditText.setOnClickListener {
            openDatePicker(dateEditText)
        }
        dateEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.updateStartup()
                val text = dateEditText.text.toString()
                val valid = text.isNotEmpty() && viewModel.getTimeMillis().dateIsInFuture()
                viewModel.updateDateTextView(valid, text.formatMaturityDate())
                cdiEditText.requestFocus()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun cdiEditTextListeners(binding: FragmentHomeBinding) {
        val cdiEditText = binding.whatIsCdiPercentageEditText
        cdiEditText.setOnEditorActionListener { view, actionId, event ->
            if (EditorInfo.IME_ACTION_DONE == actionId) {
                cdiEditTextAction(binding)
                mMustFormatEditText = true
            }
            false
        }
        cdiEditText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                mMustFormatEditText = true
                cdiEditTextAction(binding)
            } else {
                val text = cdiEditText.text.toString()
                if (text.contains("%")) {
                    formatCdiEditTextOutput(binding)
                }
            }
        }
        cdiEditText.setOnClickListener {
            val text = cdiEditText.text.toString()
            if (text.isNotEmpty() && mMustFormatEditText) {
                formatCdiEditTextOutput(binding)
            }
        }
    }

    private fun openDatePicker(dateEditText: EditText) {
        val newFragment: DialogFragment = DatePickerFragment(viewModel, dateEditText)
        newFragment.show(fragmentManager!!, "DatePicker")
    }

    private fun cdiEditTextAction(binding: FragmentHomeBinding) {
        val cdiEditText = binding.whatIsCdiPercentageEditText
        viewModel.updateStartup()
        val editTextString = cdiEditText.text.toString()
        val invalidText = editTextString.isEmpty() || (editTextString == ".")
        if (invalidText) {
            cdiEditText.setText("")
            viewModel.updateCdiEditText(false, 0.0)
        } else {
            val text = editTextString.currencyToDouble().toString()
            if (text.isNotEmpty()) {
                val valid = (text.isNotEmpty()) && (text.toDouble() > 0)
                viewModel.updateCdiEditText(valid, text.toDouble())
                val outputText = "${text.toDouble().formatPercentage()}%"
                updateCdiTextValue(cdiEditText, outputText)
            }
        }
    }

    private fun formatCdiEditTextOutput(binding: FragmentHomeBinding) {
        val cdiEditText = binding.whatIsCdiPercentageEditText
        val text = cdiEditText.text.toString()
        val formattedText = text.formatPercentage()
        updateCdiTextValue(cdiEditText, formattedText)
        mMustFormatEditText = false
    }

    private fun updateCdiTextValue(cdiEditText: EditText, text: String) {
        cdiEditText.setText(text)
        cdiEditText.setSelection(text.length)
    }
}