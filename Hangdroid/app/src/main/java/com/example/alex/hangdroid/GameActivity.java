package com.example.alex.hangdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public String myWord="";

    int mfailCounter=0;

    int mLetterGuesed=0;

    int mPoints=0;



    List<Character> sameLetters=new ArrayList<Character>();
    List<Character> noDuplicate=new ArrayList<Character>();


    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);




        setRandomWord();


        Log.d("MYLOG","This my word after first call setRandomWord "+myWord);



        bt=findViewById(R.id.buttonInsert);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText et=findViewById(R.id.editTextLetter);

                String message=et.getText().toString();

                et.setText("");  //kad se ukuca neko slovo i klikni se na button,brisemo slovo iz edittexta a prethodno slovo se prenosi kroz polje message

                Log.d("MYLOG","This my letter "+message);

                if(message.length()==1){
                    Log.d("MYLOG","word length "+myWord.length());
                    checkLetter(message);
                }

                else {
                    Toast.makeText(GameActivity.this,"Insert your letter",Toast.LENGTH_LONG).show();}
            }

        });
    }




    public void checkLetter(String wordInput){

        char charWordInput=wordInput.charAt(0);
        boolean letterguesed=false;

        for(int i=0; i<myWord.length();i++){

            char myWordChar=myWord.charAt(i);

            if(charWordInput==myWordChar){

                mLetterGuesed++;

                Log.d("MYLOG","This is mletterguesed after first increment "+mLetterGuesed  );

                letterguesed=true;

                showLetter(i,charWordInput);

              checkSameWord(charWordInput);

            }


        }


        if(letterguesed==true){
        sameLetters.add(charWordInput);
        Log.d("MYLOG","THESE ARE MEMBERS OF SAMELETTERS LIST "+sameLetters  );

        noDuplicate=new ArrayList<>(new HashSet<Character>(sameLetters));
        Log.d("MYLOG","THESE ARE MEMBERS OF NO DUPLICATE LIST "+noDuplicate  );
        }


        Log.d("MYLOG","THIS IS MLETTERGUESED COMPARED WITH MYWORDLENGTH "+mLetterGuesed  );


        if(mLetterGuesed==myWord.length()){

            Log.d("MYLOG","This is mletterguesed "+mLetterGuesed +" compare with wordLength "+myWord.length() );

            mPoints++;

            clearScreen();

            setRandomWord();
        }






        if(letterguesed==false){

        letterFailed(charWordInput);}


    }








    public void showLetter(int position,char checkWord){


        LinearLayout parent=findViewById(R.id.layoutLetters);

        TextView tv=(TextView) parent.getChildAt(position);

        tv.setText(Character.toString(checkWord));

    }



    public void letterFailed(char catchFailed){



        TextView catchFailedLetter=findViewById(R.id.showFailedLetter);

        String previous=catchFailedLetter.getText().toString();

        catchFailedLetter.setText(Character.toString(catchFailed)+previous);


         mfailCounter++;





        ImageView imageView=findViewById(R.id.imageView);


        if(mfailCounter==1){

        imageView.setImageResource(R.drawable.hangdroid_0);}

        else if(mfailCounter==2){imageView.setImageResource(R.drawable.hangdroid_1);}

        else if(mfailCounter==3){imageView.setImageResource(R.drawable.hangdroid_2);}

        else if(mfailCounter==4){imageView.setImageResource(R.drawable.hangdroid_3);}

        else if(mfailCounter==5){imageView.setImageResource(R.drawable.hangdroid_4);}

        else if(mfailCounter==6){



            Intent intent=new Intent(this,GameOverActivity.class);

            String points=String.valueOf(mPoints);

            intent.putExtra("SHOW_POINTS",points);

            startActivity(intent);

            finish();

        }

    }


    public void clearScreen(){

        mLetterGuesed=0;

        mfailCounter=0;

        sameLetters=new ArrayList<>();

        noDuplicate=new ArrayList<>();

        TextView catchFailedLetter=findViewById(R.id.showFailedLetter);

        catchFailedLetter.setText("");

        LinearLayout children=findViewById(R.id.layoutLetters);

        //int countChildren=children.getChildCount();

        int countChildren=myWord.length();

        for(int i=0; i<countChildren;i++){

            TextView everyChild= (TextView) children.getChildAt(i);



            everyChild.setText(" ");



        }



        ImageView image=findViewById(R.id.imageView);

        image.setImageResource(0);

        Log.d("MYLOG","This my word inside CLEAR SCREEN "+myWord);


    }





    public void setRandomWord(){


        String movies="THE WAY OUT,LOGAN,BATMAN,TERMINATOR";

        //geostorm equilibrium gattaca contact prestige inception batman thor town

        String myMovies[]=movies.split(",");

        Random random=new Random();

        int number=random.nextInt(4);

        myWord=myMovies[number].toLowerCase();

        Log.d("MYLOG","This is inside methode setRandomWord "+myWord);




        for(int i=0;i<myWord.length();i++){

            LinearLayout linearLayout=findViewById(R.id.layoutLetters);

            Log.d("MYLOG","SEARCHINH BLANK SPACE "+myWord.charAt(i));

            TextView children= (TextView) linearLayout.getChildAt(i);

            char blankSpace=myWord.charAt(i);
            String blankSpaceText=Character.toString(blankSpace);

            if(blankSpaceText.equals(" ")){children.setText(" ");mLetterGuesed++;}

            else{children.setText("_");}




        }

    }




    public void checkSameWord(char charWordInput){


        Log.d("MYLOG","THIS IS NO DUPLICATE LIST SIZE  "+noDuplicate.size());

        for(int i=0;i<noDuplicate.size();i++){

            if(noDuplicate.get(i)==charWordInput){

                Log.d("MYLOG","This is compared list memebers  "+noDuplicate.get(i) + " with input letter "+charWordInput);

                mLetterGuesed--;

                Log.d("MYLOG","THIS IS MLETTERGUESED AFTER DECREMENT "+mLetterGuesed  );

                Toast.makeText(GameActivity.this,"You already have that letter",Toast.LENGTH_SHORT).show();

            }


        }



    }



    public void removeWordBlankSpaces(){





    }



}
