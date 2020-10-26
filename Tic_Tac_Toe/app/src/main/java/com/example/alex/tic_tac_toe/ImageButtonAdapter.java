package com.example.alex.tic_tac_toe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alex on 3/27/2018.
 */

public class ImageButtonAdapter extends BaseAdapter {
    private Context c;
    ArrayList<ImageButton> available;
    ImageButton[] buttons;
//int i;
    public ImageButtonAdapter(Context c){

        this.c=c;
        available=new ArrayList<ImageButton>();
        buttons=new ImageButton[9];
        StartGame();
        Log.d("MYLOG ", "this is game over status" );


    }

    public void StartGame()  { for ( int i = 0; i < buttons.length; i++) {

        buttons[i] = new ImageButton(c);
        buttons[i].setBackgroundResource(R.drawable.tic_tac_toe);
        buttons[i].setLayoutParams(new GridView.LayoutParams(150,150));
        //(new GridView.LayoutParams(150, 150))

        buttons[i].setTag(0);

        available.add(buttons[i]);

        buttons[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ImageButton btn = (ImageButton) v;

                if (((Integer) btn.getTag()) == 0) {

                    btn.setTag(1);//computer moves ce moci pregaziti sa svojim potezom X iako je tag setovan na 1 posto na computer moves nema uslov
                    //kao sto je iznad stavljeno da ce se X iscrtati samo ako je tag=0,tek kad remove btn computer ce ga zaobici
                    //sa ovim je rjeseno da kompjuter ne pregazi X,ali kad bi user recimo opet kliknuo na taj btn koji removed racunao bi se
                    //opet kao userov potez a kompjuter bi odmah posle tog negde O stavio,treba staviti da click bude disablovan za korisnika
                    //tj. da X stavi u neko polje koje nije popunjeno kako se ne bi desilo da mu racuna potez kad klikne na polje koje je vec
                    //stavio prethodno X

                    available.remove(btn);

                    btn.setBackgroundResource(R.drawable.x);
                    //available.clear();

                    if(!CheckGameStatus(1)) return ;//kada se pogodi red,kolon ili dijagonala u metodi checkstatus ce biti return false kako bi ova provera bila true
                    //ovo znaci da posle userovog poteza izlazi se iz metode onclick i samim tim posle tog userovog zadnjeg poteza nece computer da odigra potez
                    //bice izbacen dijalog da je X won i izace iz metode,da ovoga  nema posle dijaloga bi computer odigrao potez u pozadini

                } else return;
               // onemoguceno da kad je vec tag setovan na 1 da se ide na computer moves,tj. kad se pokusa kliknuti
                //na vec dodeljeni X znak koji je vec prethodno setovan na tag 1 nece ici na potez racunara vec samo kad tag bude ==0
                //prece na computer moves


                //Computer move


                if (available.size() > 0) {//kad se ne bi remove btn radio,onda bi se posle random broja za klik nekog buttona proveravalo da li mu je button
                    //setTag 0 posto posle dodeljenog X znaka bice settag 1 a posle O znaka settag2

                    Random rno = new Random();

                    int nexti = rno.nextInt(available.size() - 1);
                    available.get(nexti).setTag(2);//znaci u available listi ostaju samo button setovani na nuli i kad se izabere neki nasumicno,comp ga setuje na tag 2
                    //i onda se taj button izbacuje iz liste kao kad user napravi potez i setuje se na tag 1


                    available.get(nexti).setBackgroundResource(R.drawable.o);
                    if(!CheckGameStatus(2)) return;
                    available.remove(available.get(nexti));

                }

            }


        });


    }
    }





    @Override
    public int getCount() {
        return buttons.length;
    }

    @Override
    public Object getItem(int i) {
        return buttons[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {



        return buttons[i];
    }


    boolean CheckGameStatus(int tagcheck) {

        String winningMessage = ((tagcheck == 1) ? "X" : "O")+c.getString(R.string.wins);
      boolean GameOver=false;


        //check rows

if(!GameOver)

        for (int i = 0; i < 9; i += 3) {

            if ((Integer) buttons[i].getTag() == tagcheck &&
                    (Integer) buttons[i + 1].getTag() == tagcheck &&
                    (Integer) buttons[i + 2].getTag() == tagcheck)



            {


                GameOver=true;
                break;//recimo kad bi prvi red bio pogodjen,znaci i=0,nece se prolaziti vise kroz ostatak petlje tj da je i=3 i 6,ne bi smetalo ali da se ne trosi vreme kad se vec pogodi jedan red,nema potrebe dalje prolaziti
                //znaci posle breaka izlazimo iz date for petlje i prolazi se kroz dalji kod
            }

        }



//check columns

        if(!GameOver)
        for (int i = 0; i < 3; i += 3) {

            if ((Integer) buttons[i].getTag() == tagcheck &&
                    (Integer) buttons[i + 3].getTag() == tagcheck &&
                    (Integer) buttons[i + 6].getTag() == tagcheck)

            {
                GameOver=true;
                break;
            }

        }



        //check diagonals


        if(!GameOver)

            if (



                    ( (Integer) buttons[0].getTag() == tagcheck &&
                    (Integer) buttons[4].getTag() == tagcheck &&
                    (Integer) buttons[8].getTag() == tagcheck) ||


                            ( (Integer) buttons[2].getTag() == tagcheck &&
                                    (Integer) buttons[4].getTag() == tagcheck &&
                                    (Integer) buttons[6].getTag() == tagcheck)

                    )GameOver=true;

        if(GameOver){

            AlertDialog.Builder builder=new AlertDialog.Builder(c);

            builder.setMessage(winningMessage + "\nPlay Again?").setCancelable(false).setPositiveButton(
                    "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {

                    available.clear();
                    //StartGame();
            resetGame();


                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {

                    Activity a=(Activity) c;

                    a.setContentView(R.layout.activity_main);

                }
            });

            AlertDialog alert=builder.create();
            alert.show();


        }




        Log.d("MYLOG ", "this is gameOver status from method check status"+GameOver );



if(GameOver) return false; else return true;



    }

    public void resetGame(){


        for (int i = 0; i < buttons.length; i++){

           // buttons[i] = new ImageButton(c);
            buttons[i].setBackgroundResource(R.drawable.tic_tac_toe);
            buttons[i].setLayoutParams(new GridView.LayoutParams(150, 150));


            buttons[i].setTag(0);

            available.add(buttons[i]);




        }


}



}

