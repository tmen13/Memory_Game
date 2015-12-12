package tmen.memorygame;

import android.graphics.Bitmap;
import android.media.Image;

class Card {
    int cardCover = R.mipmap.ic_card_cover;
    int cardFront = -1;
    static int cardID = 0;

    public Card(int cardFront) {
        this.cardFront = cardFront;
        cardID++;
    }

    public int getCardCover() {
        return cardCover;
    }

    public int getCardFront() {
        return cardFront;
    }

    public static int getCardID() {
        return cardID;
    }
}
