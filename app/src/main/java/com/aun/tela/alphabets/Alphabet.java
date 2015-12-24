package com.aun.tela.alphabets;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Alphabet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabet_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void open_a(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(0)).addToBackStack(null).commit();
    }

    public void open_b(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(1)).addToBackStack(null).commit();
    }

    public void open_c(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(2)).addToBackStack(null).commit();
    }

    public void open_d(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(3)).addToBackStack(null).commit();
    }

    public void open_e(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(4)).addToBackStack(null).commit();
    }

    public void open_f(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(5)).addToBackStack(null).commit();
    }

    public void open_g(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(6)).addToBackStack(null).commit();
    }

    public void open_h(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(7)).addToBackStack(null).commit();
    }

    public void open_i(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(8)).addToBackStack(null).commit();
    }

    public void open_j(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(9)).addToBackStack(null).commit();
    }

    public void open_k(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(10)).addToBackStack(null).commit();
    }

    public void open_l(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(11)).addToBackStack(null).commit();
    }

    public void open_m(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(12)).addToBackStack(null).commit();
    }

    public void open_n(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(13)).addToBackStack(null).commit();
    }

    public void open_o(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(14)).addToBackStack(null).commit();
    }

    public void open_p(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(15)).addToBackStack(null).commit();
    }

    public void open_q(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(16)).addToBackStack(null).commit();
    }

    public void open_r(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(17)).addToBackStack(null).commit();
    }

    public void open_s(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(18)).addToBackStack(null).commit();
    }

    public void open_t(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(19)).addToBackStack(null).commit();
    }

    public void open_u(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(20)).addToBackStack(null).commit();
    }

    public void open_v(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(21)).addToBackStack(null).commit();
    }

    public void open_w(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(22)).addToBackStack(null).commit();
    }

    public void open_x(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(23)).addToBackStack(null).commit();
    }

    public void open_y(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(24)).addToBackStack(null).commit();
    }

    public void open_z(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LetterFragment.getInstance(25)).addToBackStack(null).commit();
    }

    public static class LetterFragment extends Fragment {

        public static LetterFragment getInstance(int position){
            LetterFragment fragment = new LetterFragment();
            fragment.position = position;
            return fragment;
        }

        private View rootView;

        int position;
        Cursor cursor;

        private ImageView image;
        TextView letter_big, letter_detail;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.alphabet_fragment_layout, container, false);
            image = (ImageView) findViewById(R.id.image);
            letter_big = (TextView) findViewById(R.id.letter_big);
            letter_detail=(TextView) findViewById(R.id.letter_detail);

            SQLiteDatabase db = new Database(super.getActivity().getApplicationContext()).getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM "+Database.Tables.ALPHABETS, null);
            cursor.moveToPosition(position);
            String alphabet = cursor.getString(cursor.getColumnIndex("alphabet"));
            String[] names = cursor.getString(cursor.getColumnIndex("names")).split(",");
            String[] images = cursor.getString(cursor.getColumnIndex("images")).split(",");

            int image1 = Database.getResPathFromName(images[0], super.getActivity());
            image.setImageResource(image1);
            letter_big.setText(alphabet);
            letter_detail.setText(names[0]);
            ((Alphabet) super.getActivity()).setTitle("Letter " + alphabet);
            return rootView;
        }

        @Override
        public void onDestroy() {
            ((Alphabet) super.getActivity()).setTitle("The Alphabet");
            super.onDestroy();
            try{
                cursor.close();
            }catch (Exception e){

            }
        }

        public View findViewById(int res){
            return rootView.findViewById(res);
        }
    }
}