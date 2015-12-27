package tmen.memorygame.Classes;

import android.graphics.Bitmap;
import android.media.Image;

import tmen.memorygame.R;

public class Card {
    protected int cardCover = R.mipmap.ic_card_cover;
    protected int cardFront = -1;
    protected int cardID = 0;

    public Card(int cardID, int cardFront) {
        this.cardID = cardID;
        this.cardFront = cardFront;
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
