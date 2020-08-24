package br.cericatto.easynvest

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import br.cericatto.easynvest.utils.twoDecimals
import java.util.*

class DatePickerFragment(val editText: EditText) : DialogFragment(), OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val yy: Int = calendar.get(Calendar.YEAR)
        val mm: Int = calendar.get(Calendar.MONTH)
        val dd: Int = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(), this, yy, mm, dd)
    }

    override fun onDateSet(view: DatePicker, yy: Int, mm: Int, dd: Int) {
        populateSetDate(yy, mm + 1, dd)
    }

    private fun populateSetDate(year: Int, month: Int, day: Int) {
        editText.setText("${day.toString().twoDecimals()}/${month.toString().twoDecimals()}/$year")
    }
}