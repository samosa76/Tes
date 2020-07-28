package com.osa.tes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.osa.tes.Adapter.GridViewAnswerAdapter;
import com.osa.tes.Adapter.GridViewSuggestAdapter;
import com.osa.tes.Common.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;

    public Button btnSubmmit;

    public GridView gridViewAnswer,gridViewSuggest;

    public ImageView imgViewQuest;

    int[] image_list = {

            R.drawable.bola,
            R.drawable.gelas,
            R.drawable.tabung

    };

    public char[] answer;
    public char[] jawab;

    String correct_answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialization();

    }

    private void Initialization() {

        gridViewAnswer  = (GridView)findViewById(R.id.gridViewAnswer);
        gridViewSuggest = (GridView)findViewById(R.id.gridViewOption);

        imgViewQuest = (ImageView)findViewById(R.id.imgTes);

        setUpList();

        btnSubmmit = (Button)findViewById(R.id.btnSubmmit);

        btnSubmmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result= "";
                for (int i=0; i< Common.user_submmit_answer.length; i++)
                    result+=String.valueOf(Common.user_submmit_answer[i]);

                if (result.equals(correct_answer)){
                    Toast.makeText(getApplicationContext(), "Betull", Toast.LENGTH_SHORT).show();

                    //reset
                    Common.count = 0;
                    Common.user_submmit_answer = new char[correct_answer.length()];

                    //set adapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setUpNull(),getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource, getApplicationContext(),MainActivity.this);
                    gridViewAnswer.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();
                    
                    setUpList();
                }
                else{
                    Toast.makeText(MainActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setUpList() {

        //Random Image
        Random random = new Random();
        int imageSelected = image_list[random.nextInt(image_list.length)];
        imgViewQuest.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

        answer = correct_answer.toCharArray();

//        String suggest = Common.alphabet_character.toString().replaceAll(",","");
//        jawab = suggest.substring(1, suggest.length()-1).replaceAll(" ","").toCharArray();

        Common.user_submmit_answer = new char[answer.length];

        //add answer to list
        suggestSource.clear();
        for (char item:answer)
        {
            suggestSource.add(String.valueOf(item));
        }

        //Random add some character
        for (int i = Common.alphabet_character.length; i<Common.alphabet_character.length*2; i++)
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

        //mengacak urutan
        Collections.shuffle(suggestSource);

        //set
        answerAdapter = new GridViewAnswerAdapter(setUpNull(),this);
        suggestAdapter = new GridViewSuggestAdapter(suggestSource,this,this);

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

    }

    private char[] setUpNull() {

        char result[]=new char[answer.length];
        for (int i = 0; i <answer.length; i++)
            result[i]=' ';

        return result;
    }
}
