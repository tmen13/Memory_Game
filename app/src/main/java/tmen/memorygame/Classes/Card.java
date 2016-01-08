package tmen.memorygame.Classes;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

import tmen.memorygame.R;

public class Card implements Serializable {
    protected int cardCover = R.mipmap.ic_card_cover;
    protected int cardFront = -1;
    protected String cardFrontStr;
    protected int cardID = 0;
    protected String tema;

    public Card(int cardID, int cardFront, String tema) {
        this.cardID = cardID;
        this.cardFront = cardFront;
        this.tema = tema;
    }

    public Card(int cardID, String cardFrontStr, String tema) {
        this.cardID = cardID;
        this.cardFrontStr = cardFrontStr;
        this.tema = tema;
    }

    public int getCardCover() {
        return cardCover;
    }

    public int getCardFront() {
        return cardFront;
    }

    public String getCardFrontStr() {
        return cardFrontStr;
    }

    public /*static*/ int getCardID() {
        return cardID;
    }

    public String getTema() {
        return tema;
    }
}
