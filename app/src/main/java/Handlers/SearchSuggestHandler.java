package Handlers;

import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class SearchSuggestHandler {

    public void onCreate() {}
    public ArrayList<SearchResult> setSuggest(MainActivity m) {

        ArrayList<SearchResult> suggestList = new ArrayList<>();
        suggestList.add(new SearchResult("Wine", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Vodka", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Beer", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Whiskey", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Scotch", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Hennessy", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Tequila", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Rum", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Brandy", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Gin", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));
        suggestList.add(new SearchResult("Sake", m.getResources().getDrawable(
                R.drawable.ic_action_history, null)));

        return suggestList;
    }
}
