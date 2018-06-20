package com.example.jramos.myeasybible;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jramos.myeasybible.models.Chapter;
import com.example.jramos.myeasybible.models.Verse;
import com.example.jramos.myeasybible.utils.Dialogs;
import com.example.jramos.myeasybible.utils.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookChapter.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookChapter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookChapter extends Fragment {

    private Chapter chapter;
    private OnFragmentInteractionListener mListener;
    private Dialogs.QuestionDialog addToBookmarksDialog;
    private String bookname;
    JSONObject bookmark;

    public BookChapter() {
        // Required empty public constructor
    }


    public static BookChapter newInstance(Chapter _chapter, String _bookname) {

        BookChapter fragment = new BookChapter();
        fragment.setChapter(_chapter);
        fragment.setBookname(_bookname);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setChapter(Chapter _chapter){
        chapter = _chapter;
    }
    private void setBookname(String _name){
        bookname = _name;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_chapter, container, false);

        ListView lvVerses = view.findViewById(R.id.lvVerses_list);
        ArrayList<String> list = new ArrayList<>();
        List<Verse> verses = chapter.getVerses();

        for(Verse _verse : verses){
            String text =_verse.getVerseNumber() + ". " +_verse.getText();
            list.add(text);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, list);
        lvVerses.setAdapter(adapter);
        lvVerses.setDividerHeight(0);


        addToBookmarksDialog = new Dialogs.QuestionDialog(this.getActivity(), "Desea agregar este verso a marcadores?");

        addToBookmarksDialog.setOnPositiveAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(bookmark != null)
                        addToBookmarks(bookmark);

                }catch (Exception e){
                    Log.e("JSON EXCEPT", e.getMessage());
                }
            }
        });

        lvVerses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                try{
                    int verseNumber = chapter.getVerseAt(position).getVerseNumber();
                    int chapterNumber = chapter.getChapterNumber();
                    bookmark = new JSONObject();
                    bookmark.put("bookname", bookname);
                    bookmark.put("chapterNumber", chapterNumber);
                    bookmark.put("verseNumber", verseNumber);

                    addToBookmarksDialog.show();

                }catch (Exception e){
                    Log.e("JSON EXCEPT", e.getMessage());
                }

                return false;
            }
        });

        return view;
    }

    public void addToBookmarks(JSONObject _bookmark) throws JSONException {

        String key = getResources().getString(R.string.bookmarks_pref_key);
        String bookmark_list = Preferences.getStringValue(this.getActivity(), key);
        if(bookmark_list.isEmpty()){
            JSONArray array = new JSONArray();
            array.put(_bookmark);

            JSONObject root = new JSONObject();
            root.put("bookmarks", array);

            Preferences.putString(this.getActivity(), key, root.toString());
            return;
        }


        JSONObject root = new JSONObject(bookmark_list);
        JSONArray array = root.optJSONArray("bookmarks");

        for (int i = 0; i < array.length(); i++){
            String bookmarkStr = array.optString(i);
            if(bookmarkStr.equals(_bookmark.toString()))
                return;
        }

        array.put(_bookmark);

        root = new JSONObject();
        root.put("bookmarks", array);

        Preferences.putString(this.getActivity(), key, root.toString());
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
