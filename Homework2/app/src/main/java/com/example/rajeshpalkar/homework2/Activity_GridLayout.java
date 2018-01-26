package com.example.rajeshpalkar.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_GridLayout extends AppCompatActivity implements View.OnClickListener{

    Button button0 , button1 , button2 , button3 , button4 , button5 , button6 ,
            button7 , button8 , button9 , buttonAdd , buttonSub , buttonDivision ,
            buttonMul , button00 , buttonC , buttonEqual ;

    EditText edt1 ;

    float mValueOne , mValueTwo ;

    boolean mAddition , mSubtract ,mMultiplication ,mDivision,mPercentage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridlayout);

        button0 = (Button) findViewById(R.id.zero);
        button1 = (Button) findViewById(R.id.one);
        button2 = (Button) findViewById(R.id.two);
        button3 = (Button) findViewById(R.id.three);
        button4 = (Button) findViewById(R.id.four);
        button5 = (Button) findViewById(R.id.five);
        button6 = (Button) findViewById(R.id.six);
        button7 = (Button) findViewById(R.id.seven);
        button8 = (Button) findViewById(R.id.eight);
        button9 = (Button) findViewById(R.id.nine);
        button00 = (Button) findViewById(R.id.zeroo);
        buttonAdd = (Button) findViewById(R.id.add);
        buttonSub = (Button) findViewById(R.id.sub);
        buttonMul = (Button) findViewById(R.id.mul);
        buttonDivision = (Button) findViewById(R.id.div);
        buttonC = (Button) findViewById(R.id.clear);
        buttonEqual = (Button) findViewById(R.id.equal);
        edt1 = (EditText) findViewById(R.id.display);



        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button00.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);

    }


    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.one:
                edt1.setText(edt1.getText()+"1");
                break;
            case R.id.two:
                edt1.setText(edt1.getText()+"2");
                break;
            case R.id.three:
                edt1.setText(edt1.getText()+"3");
                break;
            case R.id.four:
                edt1.setText(edt1.getText()+"4");
                break;
            case R.id.five:
                edt1.setText(edt1.getText()+"5");
                break;
            case R.id.six:
                edt1.setText(edt1.getText()+"6");
                break;
            case R.id.seven:
                edt1.setText(edt1.getText()+"7");
                break;
            case R.id.eight:
                edt1.setText(edt1.getText()+"8");
                break;
            case R.id.nine:
                edt1.setText(edt1.getText()+"9");
                break;
            case R.id.zero:
                edt1.setText(edt1.getText()+"0");
                break;
            case R.id.zeroo:
                edt1.setText(edt1.getText()+"00");
                break;
            case R.id.add:
                if (edt1 == null){
                    edt1.setText("");
                }else {
                    mValueOne = Float.parseFloat(edt1.getText() + "");
                    mAddition = true;
                    edt1.setText(null);
                }
                break;
            case R.id.sub:
                if (edt1 == null){
                    edt1.setText("");
                }else {
                    mValueOne = Float.parseFloat(edt1.getText() + "");
                    mSubtract = true;
                    edt1.setText(null);
                }
                break;
            case R.id.mul:
                if (edt1 == null){
                    edt1.setText("");
                }else {
                    mValueOne = Float.parseFloat(edt1.getText() + "");
                    mMultiplication = true;
                    edt1.setText(null);
                }
                break;
            case R.id.div:
                if (edt1 == null){
                    edt1.setText("");
                }else {
                    mValueOne = Float.parseFloat(edt1.getText() + "");
                    mDivision = true;
                    edt1.setText(null);
                }
                break;
            case R.id.per:
                if (edt1 == null){
                    edt1.setText("");
                }else {
                    mValueOne = Float.parseFloat(edt1.getText() + "");
                    mPercentage = true;
                    edt1.setText(null);
                }
                break;
            case R.id.equal:
                mValueTwo = Float.parseFloat(edt1.getText() + "");

                if (mAddition == true){

                    edt1.setText(mValueOne + mValueTwo +"");
                    mAddition=false;
                }


                if (mSubtract == true){
                    edt1.setText(mValueOne - mValueTwo+"");
                    mSubtract=false;
                }

                if (mMultiplication == true){
                    edt1.setText(mValueOne * mValueTwo+"");
                    mMultiplication=false;
                }

                if (mDivision == true){
                    edt1.setText(mValueOne / mValueTwo+"");
                    mDivision=false;
                }
                if (mPercentage == true){
                    edt1.setText(mValueOne % mValueTwo+"");
                    mDivision=false;
                }
                break;
            case R.id.clear:
                edt1.setText("");
                break;
            case R.id.dot:
                edt1.setText(edt1.getText()+".");
                break;


        }
    }


}
