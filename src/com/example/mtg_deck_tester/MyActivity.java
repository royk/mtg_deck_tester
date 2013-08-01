package com.example.mtg_deck_tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(openDeckFinder);
    }

    View.OnClickListener openDeckFinder = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText landsInput = (EditText)findViewById(R.id.editTextLands);
            EditText creaturesInput = (EditText)findViewById(R.id.editTextCreatures);
            EditText othersInput = (EditText)findViewById(R.id.editTextOthers);
            Integer lands = Integer.parseInt(landsInput.getText().toString());
            Integer creatures = Integer.parseInt(creaturesInput.getText().toString());
            Integer others = Integer.parseInt(othersInput.getText().toString());
            Integer totalCards = lands + creatures + others;
            Integer drawCount = 1000;
            HashMap<String, Integer> hands = new HashMap<String, Integer>();
            for (int i=0; i<drawCount; i++) {
                List<Card> hand = getHand(totalCards, lands, creatures, others);
                int _lands = 0;
                int _creatures = 0;
                int _others = 0;
                while (hand.size()>0) {
                    Card card = hand.remove(0);
                    if (card.type==Card.CardType.Creature) _creatures++;
                    if (card.type==Card.CardType.Land) _lands++;
                    if (card.type==Card.CardType.Other) _others++;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("l ").append(_lands).append(" c ").append(_creatures).append(" o ").append(_others);
                String handHash = sb.toString();
                Integer handCount = hands.get(handHash);
                if (handCount==null) handCount = 0;
                handCount++;
                hands.put(handHash, handCount);
            }
            List<TextView> resultViews = new ArrayList<TextView>();
            resultViews.add((TextView)findViewById(R.id.textViewResult1));
            resultViews.add((TextView)findViewById(R.id.textViewResult2));
            resultViews.add((TextView)findViewById(R.id.textViewResult3));
            for (int i=0; i<3; i++) {
                Integer maxHandCount = 0;
                String mostCommonHand = "";
                for (Map.Entry<String, Integer> entry : hands.entrySet()) {
                    if (entry.getValue()>maxHandCount) {
                        maxHandCount = entry.getValue();
                        mostCommonHand = entry.getKey();
                    }
                }
                Double chance = (double)maxHandCount/drawCount*100;
                StringBuilder sb = new StringBuilder();
                sb.append(mostCommonHand).append("  ").append(chance).append("%");
                resultViews.get(i).setText(sb.toString());
                hands.remove(mostCommonHand);
            }
        }
    };
    List<Card> deck;
    private List<Card> getHand(Integer total, Integer lands, Integer creatures, Integer others) {
        deck = new ArrayList<Card>(total);
        List<Integer> positions = new ArrayList<Integer>(total);
        for (int i=0; i<total; i++) {
            positions.add(i);
            deck.add(new Card());
        }
        for (int i=0; i<total; i++) {
            int pos1 = (int)Math.floor(Math.random()*(positions.size()-1));
            int pos = positions.remove(pos1);
            Card card = deck.get(pos);
            if (lands>0) {
                lands--;
                card.type = Card.CardType.Land;
            } else
            if (creatures>0) {
                creatures--;
                card.type = Card.CardType.Creature;
            } else
            if (others>0) {
                others--;
                card.type = Card.CardType.Other;
            }
        }
        List<Card> hand = new ArrayList<Card>(7);
        for (int i=0; i<7; i++) {
            hand.add(deck.remove(0));
        }
        return hand;

    }

}

