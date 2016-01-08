package tmen.memorygame.Adapters;

import android.content.Context;
import android.text.Layout;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tmen.memorygame.Activities.JogoActivity;
import tmen.memorygame.Classes.Historico;
import tmen.memorygame.Classes.Tema;
import tmen.memorygame.R;

/**
 * Created by Ricardo on 07/01/2016.
 */
public class HistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<Historico> historicoList ;

    public HistoryAdapter(Context mContext, List<Historico> historicoList) {
        this.mContext = mContext;
        this.historicoList = historicoList;
    }

    @Override
    public int getCount() {
        return historicoList.size();
    }

    @Override
    public Historico getItem(int position) {
        return historicoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private int tipo;
    private String tema;
    private String nomeJogador1, nomeJogador2;
    private int tentativas[] = { 0, 0 };
    private int acertadas[] = { 0, 0 };
    private int intrusosAcertados[] = { 0, 0 };
    private int vencedor;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout mainLinearLayout = new LinearLayout(mContext);
        Log.d("MemoryGame", "Tipo: " + getItem(position).getTipo() + " NomeJogador1: " + getItem(position).getNomeJogador1() + " NomeJogador2: " + getItem(position).getNomeJogador2() + " TentativasJogador1: " + getItem(position).getTentativas(0) + " TentativasJogador2: " + getItem(position).getTentativas(1) + " AcertadasJogador1: " + getItem(position).getAcertadas(0) + " AcertadasJogador2: " + getItem(position).getAcertadas(1) + " IntrusosJogador1: " + getItem(position).getIntrusosAcertados(0) + " IntrusosJogador2: " + getItem(position).getIntrusosAcertados(1) + " Vencedor: " + getItem(position).getVencedor());



        TextView tipoTextView, temaTextView, nomeJogador1TextView, nomeJogador2TextView, tentativasJogador1TextView, tentativasJogador2TextView, acertadasJogador1TextView, acertadasJogador2TextView, intrusosAcertadosJogador1TextView, intrusosAcertadosJogador2TextView, nomeVencedorTextView;
        //if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LinearLayout infoJogoLinearLayout = new LinearLayout(mContext);
            LinearLayout infoJogadoresLinearLayout = new LinearLayout(mContext);
            LinearLayout infoJogador1LinearLayout = new LinearLayout(mContext);
            LinearLayout infoJogador2LinearLayout = new LinearLayout(mContext);

            AbsListView.LayoutParams absListLayoutParams = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);

        AbsListView.LayoutParams absListWidthMatchParentLayoutParams = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);

            LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams linearLayoutWidthMatchParentLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mainLinearLayout.setLayoutParams(absListLayoutParams);
        infoJogoLinearLayout.setLayoutParams(linearLayoutLayoutParams);
        infoJogadoresLinearLayout.setLayoutParams(linearLayoutLayoutParams);
        infoJogador1LinearLayout.setLayoutParams(linearLayoutLayoutParams);
        infoJogador2LinearLayout.setLayoutParams(linearLayoutLayoutParams);


            mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
        infoJogoLinearLayout.setOrientation(LinearLayout.VERTICAL);
            infoJogadoresLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            infoJogador1LinearLayout.setOrientation(LinearLayout.VERTICAL);
            infoJogador2LinearLayout.setOrientation(LinearLayout.VERTICAL);


            tipoTextView = new TextView(mContext);
            temaTextView = new TextView(mContext);
            nomeJogador1TextView = new TextView(mContext);
            nomeJogador2TextView = new TextView(mContext);
            tentativasJogador1TextView = new TextView(mContext);
            tentativasJogador2TextView = new TextView(mContext);
            acertadasJogador1TextView = new TextView(mContext);
            acertadasJogador2TextView = new TextView(mContext);
            intrusosAcertadosJogador1TextView = new TextView(mContext);
            intrusosAcertadosJogador2TextView = new TextView(mContext);
            nomeVencedorTextView = new TextView(mContext);

            infoJogoLinearLayout.addView(tipoTextView);
            infoJogoLinearLayout.addView(temaTextView);
            infoJogoLinearLayout.addView(nomeVencedorTextView);

            infoJogador1LinearLayout.addView(nomeJogador1TextView);
            infoJogador1LinearLayout.addView(tentativasJogador1TextView);
            infoJogador1LinearLayout.addView(acertadasJogador1TextView);
            infoJogador1LinearLayout.addView(intrusosAcertadosJogador1TextView);

            infoJogador2LinearLayout.addView(nomeJogador2TextView);
            infoJogador2LinearLayout.addView(tentativasJogador2TextView);
            infoJogador2LinearLayout.addView(acertadasJogador2TextView);
            infoJogador2LinearLayout.addView(intrusosAcertadosJogador2TextView);

            mainLinearLayout.addView(infoJogoLinearLayout);
            infoJogadoresLinearLayout.addView(infoJogador1LinearLayout);
            infoJogadoresLinearLayout.addView(infoJogador2LinearLayout);
            mainLinearLayout.addView(infoJogadoresLinearLayout);

            mainLinearLayout.setEnabled(false);
        infoJogoLinearLayout.setEnabled(false);
        infoJogadoresLinearLayout.setEnabled(false);
        infoJogador1LinearLayout.setEnabled(false);
        infoJogador2LinearLayout.setEnabled(false);
            //view = new View(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //view.setPadding(8, 8, 8, 8);

        //} else {
        //    mainLinearLayout = (LinearLayout) convertView;
        //}

        String tipo;
        if (getItem(position).getTipo() == JogoActivity.SINGLEPLAYER) {
            tipo = mContext.getString(R.string.menu_singleplayer);
        } else if (getItem(position).getTipo() == JogoActivity.MULTIPLAYER) {
            tipo = mContext.getString(R.string.menu_multiplayer);
        } else {
            tipo = mContext.getString(R.string.menu_multiplayer_on);
        }
        tipoTextView.setText(R.string.game_type + " " + tipo);
        temaTextView.setText(R.string.theme + " " + getItem(position).getTema());
        nomeJogador1TextView.setText(getItem(position).getNomeJogador1());
        if (getItem(position).getTipo() != JogoActivity.SINGLEPLAYER) {
            nomeJogador2TextView.setText(getItem(position).getNomeJogador2());
            if (getItem(position).getMode() == JogoActivity.SERVER) {
                tentativasJogador1TextView.setText(R.string.trys + " " + getItem(position).getTentativas(JogoActivity.ME));
                tentativasJogador2TextView.setText(R.string.trys + " " + getItem(position).getTentativas(JogoActivity.OTHER));
                acertadasJogador1TextView.setText(R.string.correct + " " + getItem(position).getAcertadas(JogoActivity.ME));
                acertadasJogador2TextView.setText(R.string.correct + " " + getItem(position).getAcertadas(JogoActivity.OTHER));
                intrusosAcertadosJogador1TextView.setText(R.string.intruder + " " + getItem(position).getIntrusosAcertados(JogoActivity.ME));
                intrusosAcertadosJogador2TextView.setText(R.string.intruder + " " + getItem(position).getIntrusosAcertados(JogoActivity.OTHER));
                if (getItem(position).getVencedor() == JogoActivity.ME) {
                    nomeVencedorTextView.setText(R.string.winner + " " + getItem(position).getNomeJogador1());
                } else {
                    nomeVencedorTextView.setText(R.string.winner + " " + getItem(position).getNomeJogador2());
                }
            } else {
                tentativasJogador1TextView.setText(R.string.trys + " " + getItem(position).getTentativas(JogoActivity.OTHER));
                tentativasJogador2TextView.setText(R.string.trys + " " + getItem(position).getTentativas(JogoActivity.ME));
                acertadasJogador1TextView.setText(R.string.correct + " " + getItem(position).getAcertadas(JogoActivity.OTHER));
                acertadasJogador2TextView.setText(R.string.correct + " " + getItem(position).getAcertadas(JogoActivity.ME));
                intrusosAcertadosJogador1TextView.setText(R.string.intruder + " " + getItem(position).getIntrusosAcertados(JogoActivity.OTHER));
                intrusosAcertadosJogador2TextView.setText(R.string.intruder + " " + getItem(position).getIntrusosAcertados(JogoActivity.ME));
                if (getItem(position).getVencedor() == JogoActivity.OTHER) {
                    nomeVencedorTextView.setText(R.string.winner + " " + getItem(position).getNomeJogador1());
                } else {
                    nomeVencedorTextView.setText(R.string.winner + " " + getItem(position).getNomeJogador2());
                }
            }
        } else {
            tentativasJogador1TextView.setText(R.string.trys + " " + getItem(position).getTentativas(JogoActivity.ME));
            acertadasJogador1TextView.setText(R.string.trys + " " + getItem(position).getAcertadas(JogoActivity.ME));
            intrusosAcertadosJogador1TextView.setText(R.string.intruder + " " + getItem(position).getIntrusosAcertados(JogoActivity.ME));
            nomeVencedorTextView.setText(R.string.winner + " " + getItem(position).getNomeJogador1());
            infoJogador2LinearLayout.setVisibility(View.GONE);
        }

        return mainLinearLayout;
    }
}
