package com.example.calculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import kotlin.math.floor

class MainActivity : AppCompatActivity() , View.OnClickListener{

    private var edtxt : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtxt = findViewById<EditText>(R.id.edtxt)
        findViewById<Button>(R.id.btnOne).setOnClickListener(this)
        findViewById<Button>(R.id.btnTwo).setOnClickListener(this)
        findViewById<Button>(R.id.btnThree).setOnClickListener(this)
        findViewById<Button>(R.id.btnFour).setOnClickListener(this)
        findViewById<Button>(R.id.btnFive).setOnClickListener(this)
        findViewById<Button>(R.id.btnSix).setOnClickListener(this)
        findViewById<Button>(R.id.btnSeven).setOnClickListener(this)
        findViewById<Button>(R.id.btnEight).setOnClickListener(this)
        findViewById<Button>(R.id.btnNine).setOnClickListener(this)
        findViewById<Button>(R.id.btnDot).setOnClickListener(this)
        findViewById<Button>(R.id.btnZero).setOnClickListener(this)
        findViewById<Button>(R.id.btnAC).setOnClickListener(this)
        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
        findViewById<Button>(R.id.btnPlus).setOnClickListener(this)
        findViewById<Button>(R.id.btnMinus).setOnClickListener(this)
        findViewById<Button>(R.id.btnMul).setOnClickListener(this)
        findViewById<Button>(R.id.btnDiv).setOnClickListener(this)
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener(this)
        findViewById<ImageButton>(R.id.btnSQ).setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        if(edtxt?.text.contentEquals("Infinity"))
            edtxt?.setText("")
        when(v?.id){
            R.id.btnOne-> edtxt?.append("1")
            R.id.btnTwo-> edtxt?.append("2")
            R.id.btnThree-> edtxt?.append("3")
            R.id.btnFour-> edtxt?.append("4")
            R.id.btnFive-> edtxt?.append("5")
            R.id.btnSix-> edtxt?.append("6")
            R.id.btnSeven-> edtxt?.append("7")
            R.id.btnEight-> edtxt?.append("8")
            R.id.btnNine-> edtxt?.append("9")
            R.id.btnZero-> edtxt?.append("0")
            R.id.btnAC-> edtxt?.setText("")
            R.id.btnBack-> if(edtxt?.length()!!>0)edtxt?.setText(edtxt?.text!!.substring(0, edtxt?.length()!!-1))
            R.id.btnDot-> if(isValidDot(edtxt)) edtxt?.append(".")
            R.id.btnEqual-> equalTo(edtxt)
            R.id.btnPlus-> if(isValidOperator(edtxt)) edtxt?.append("+")
            R.id.btnMinus-> if(isValidOperator(edtxt)) edtxt?.append("-")
            R.id.btnMul-> if(isValidOperator(edtxt)) edtxt?.append("*")
            R.id.btnDiv-> if(isValidOperator(edtxt)) edtxt?.append("/")

            R.id.btnSQ-> if( !(edtxt?.text.contentEquals("Infinity")) && edtxt?.text!!.length >  0 ){
                if( edtxt?.text!!.contains(Regex("[-+/*]")) )
                   equalTo(edtxt)

                var temp = String.format("%.3f", Math.sqrt(edtxt?.text.toString().toDouble()) )
                while(temp.last() == '0')
                    temp = temp.slice(0..temp.lastIndex-1)
                if(temp.last()=='.')
                    temp = temp.slice(0..temp.lastIndex-1)
                edtxt?.setText( temp )

            }
        }
    }
    private fun isValidOperator(edtxt : EditText?) : Boolean{
        if(edtxt?.text!!.length>0)
          return edtxt?.text!!.last() == '1' || edtxt?.text!!.last() == '2' || edtxt?.text!!.last() == '3' || edtxt?.text!!.last() == '4' || edtxt?.text!!.last() == '5' || edtxt?.text!!.last() == '6' || edtxt?.text!!.last() == '7' || edtxt?.text!!.last() == '8' || edtxt?.text!!.last() == '9' || edtxt?.text!!.last() == '0'
        else
          return false
    }

    private fun isValidDot(edtxt : EditText?): Boolean{
         var str : String? = edtxt?.text.toString()
         var arrstr : List<String>?= str?.split(Regex("[-+*/]"))
         return !arrstr?.last()!!.contains(".")
    }

    private fun equalTo(edtxt : EditText?){
        if(edtxt?.text!!.contains(Regex("[-+*/]"))){
            if(edtxt?.text!!.last() == '1' || edtxt?.text!!.last() == '2' || edtxt?.text!!.last() == '3' || edtxt?.text!!.last() == '4' || edtxt?.text!!.last() == '5' || edtxt?.text!!.last() == '6' || edtxt?.text!!.last() == '7' || edtxt?.text!!.last() == '8' || edtxt?.text!!.last() == '9' || edtxt?.text!!.last() == '0') {
                var arrstr: List<String>? = edtxt?.text.toString().split(Regex("[-+*/]"))
                var stack = ArrayDeque<Char>()
                var exp = ""
                var res = ""
                var count=0
                var result : Float = 0.0f
                for(i in edtxt?.text.toString().toCharArray()){
                    if(i == '+')
                        exp+=(count++).toString()+"+"
                    else if(i=='-')
                        exp+=(count++).toString()+"-"
                    else if(i=='*')
                        exp+=(count++).toString()+"*"
                    else if(i=='/')
                        exp+=(count++).toString()+"/"
                }
                exp+=count
                for(i in exp.toCharArray()){
                     if(i == '+' || i == '-' || i == '*' || i =='/'){
                         if(stack.size == 0)
                             stack.add(i)
                         else if( i == '+' || i == '-' ) {
                             while( stack.size != 0 && (stack.last()=='*' || stack.last()=='/' || stack.last()=='+' || stack.last()=='-' )) {
                                 res += stack.last()
                                 stack.removeLast()
                             }
                             stack.add(i)
                         }else if( i== '*' || i=='/' ){
                             while( stack.size != 0 && (stack.last()=='*' || stack.last()=='/' )) {
                                 res += stack.last()
                                 stack.removeLast()
                             }
                             stack.add(i)
                         }else
                             res += i
                     }else
                         res += i
                }
                while(stack.size!=0){
                    res+=stack.last()
                    stack.removeLast()
                }
                var stack2 = ArrayDeque<Float>()
                for (i in res){
                    if(i == '+' || i == '-' || i == '*' || i =='/'){
                        var num1 : Float = stack2.removeLast()
                        var num2 : Float = stack2.removeLast()
                        if(i=='+'){
                            result = num2+num1
                            stack2.add(result)
                        }else if(i=='-'){
                            result = num2-num1
                            stack2.add(result)
                        }else if(i=='*'){
                            result = num2*num1
                            stack2.add(result)
                        }else{
                            result = num2/num1
                            stack2.add(result)
                        }
                    }else{
                        stack2.add(arrstr?.get(i.code.toInt()-48)!!.toFloat())
                    }
                }
                if(result > Float.MAX_VALUE) {
                    edtxt?.setText(result.toString())
                }else{
                    arrstr = result.toString().split(".")
                    if (Integer.parseInt(arrstr[1]) == 0)
                        edtxt?.setText(arrstr[0])
                    else
                        edtxt?.setText( String.format("%.3f", result.toString().toDouble()))
                }
            }
        }
    }
}