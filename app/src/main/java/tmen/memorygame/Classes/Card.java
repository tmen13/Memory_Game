package tmen.memorygame.Classes;

import android.graphics.Bitmap;
import android.media.Image;

import tmen.memorygame.R;

public class Card {
    protected int cardCover = R.mipmap.ic_card_cover;
    protected int cardFront = -1;
    protected int cardID = 0;
    protected String tema;

    public Card(int cardID, int cardFront, String tema) {
        this.cardID = cardID;
        this.cardFront = cardFront;
        this.tema = tema;
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

    public String getTema() {
        return tema;
    }
}
