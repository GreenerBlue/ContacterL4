package com.testing.atul.contacterl4;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER;
import static android.provider.ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFrag extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{

    private static final String ARG_PARAM1 = "start";
    private static final String ARG_PARAM2 = "end";

    private char start;
    private char end;
    ListView conList;
    SimpleCursorAdapter adapter;

    private final static String[] FROM_COLUMNS = {
            DISPLAY_NAME_PRIMARY, NUMBER
    };
    private final static int[] TO_IDS = {
            R.id.b1t1, R.id.b1t2
    };

    private OnFragmentInteractionListener mListener;

    public ContactFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFrag.
     */
    public static ContactFrag newInstance(char param1, char param2) {
        ContactFrag fragment = new ContactFrag();
        Bundle args = new Bundle();
        args.putChar(ARG_PARAM1, param1);
        args.putChar(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            start = getArguments().getChar(ARG_PARAM1);
            end = getArguments().getChar(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        conList = v.findViewById(R.id.conBox);
        return v;
    }


    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle boom){
        super.onActivityCreated(boom);
        getLoaderManager().initLoader(0,null,this);
        conList = getActivity().findViewById(R.id.conBox);
        adapter = new SimpleCursorAdapter(getActivity(),R.layout.boxy,null,FROM_COLUMNS,TO_IDS,0);
        conList.setAdapter(adapter);
        conList.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri contacts = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] Projection = {ContactsContract.Contacts._ID,DISPLAY_NAME_PRIMARY, NUMBER};
        String constraint = DISPLAY_NAME_PRIMARY+" > \'"+start+"%\' AND "+ DISPLAY_NAME_PRIMARY+" < \'"+end+"%\'";
        //String constraint = DISPLAY_NAME_PRIMARY+ "> \'C%\'";
        Log.i("mouse_3",constraint);
        return new CursorLoader(getActivity(),contacts,Projection,constraint,null,DISPLAY_NAME_PRIMARY);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {  adapter.swapCursor(c);  }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {  adapter.swapCursor(null);  }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
        c.moveToPosition(position);

        Toast.makeText(getActivity(),c.getString(1),Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String uri);
    }
}
