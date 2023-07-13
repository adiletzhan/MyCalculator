package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class MainActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var resultTV: TextView; lateinit var solutionTv: TextView

    lateinit var buttonC: MaterialButton;
    lateinit var buttonOpenBracket: MaterialButton; lateinit var buttonCloseBracket: MaterialButton;

    lateinit var buttonDivide: MaterialButton; lateinit var buttonMultiply: MaterialButton;
    lateinit var buttonPlus: MaterialButton; lateinit var buttonMinus: MaterialButton;
    lateinit var buttonEqual: MaterialButton;

    lateinit var button0: MaterialButton; lateinit var button1: MaterialButton;
    lateinit var button2: MaterialButton; lateinit var button3: MaterialButton;
    lateinit var button4: MaterialButton; lateinit var button5: MaterialButton;
    lateinit var button6: MaterialButton; lateinit var button7: MaterialButton;
    lateinit var button8: MaterialButton; lateinit var button9: MaterialButton;

    lateinit var buttonAC: MaterialButton; lateinit var buttonDot: MaterialButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultTV = findViewById(R.id.result_tv)
        solutionTv = findViewById(R.id.solution_tv)

        buttonC = assignId(R.id.button_c)
        buttonOpenBracket = assignId(R.id.button_open_bracket)
        buttonCloseBracket = assignId(R.id.button_close_bracket)

        buttonDivide = assignId(R.id.button_divide)
        buttonMultiply = assignId(R.id.button_multiply)
        buttonPlus = assignId(R.id.button_plus)
        buttonMinus = assignId(R.id.button_minus)
        buttonEqual = assignId(R.id.button_equal)

        button0 = assignId(R.id.button_0)
        button1 = assignId(R.id.button_1)
        button2 = assignId(R.id.button_2)
        button3 = assignId(R.id.button_3)
        button4 = assignId(R.id.button_4)
        button5 = assignId(R.id.button_5)
        button6 = assignId(R.id.button_6)
        button7 = assignId(R.id.button_7)
        button8 = assignId(R.id.button_8)
        button9 = assignId(R.id.button_9)

        buttonAC = assignId(R.id.button_ac)
        buttonDot = assignId(R.id.button_dot)
    }

    private fun assignId(id: Int): MaterialButton{
        val btn : MaterialButton = findViewById(id)
        btn.setOnClickListener(this)
        return btn
    }

    override fun onClick(view: View) {
        val button: MaterialButton = view as MaterialButton
        val buttonText: String = button.text.toString()
        var dataToCalculate: String = solutionTv.text.toString()

        if(buttonText.equals("AC")){
            solutionTv.setText("")
            resultTV.setText("0")
            return
        }

        if(buttonText.equals("=")){
            solutionTv.setText(resultTV.text)
            return
        }

        if(buttonText.equals("C") && dataToCalculate.isNotEmpty()){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length-1)
        } else {
            dataToCalculate += buttonText
        }

        solutionTv.setText(dataToCalculate)

        var finalResult = getResult(solutionTv.text.toString())
        if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "")
            }

        if (finalResult!= "Err"){
            resultTV.setText(finalResult)
        }
    }

    fun getResult(data: String): String{

        return try {
            var context: Context = Context.enter()
            context.setOptimizationLevel(-1)

            var scriptable: Scriptable = context.initStandardObjects()

            context.evaluateString(scriptable, data, "JavaScript", 1, null).toString()
        } catch (e: Exception) {
            "Err"
        }
    }
}