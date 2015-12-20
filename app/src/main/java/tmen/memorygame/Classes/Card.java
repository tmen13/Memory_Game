package tmen.memorygame.Classes;

import android.graphics.Bitmap;
import android.media.Image;

import tmen.memorygame.R;

public class Card {
    int cardCover = R.mipmap.ic_card_cover;
    int cardFront = -1;
    /*static*/ int cardID = /*0*/-1;

    public Card(int cardID, int cardFront) {
        this.cardID = cardID;
        this.cardFront = cardFront;
        //cardID++;
    }

    public int getCardCover() {
        return cardCover;
    }

    public int getCardFront() {
        return cardFront;
    }

    public /*static*/ int getCardID() {
        return cardID;
    }
}
