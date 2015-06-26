package comjason_lewisg.httpsgithub.boozic.Handlers;


import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class SearchSuggestHandler {

    ArrayList<SearchResult> suggestList;

    public void onCreate() {
    }
    public void initList() {
        suggestList = new ArrayList<>();
    }
    public ArrayList<SearchResult> addSuggest(String searchterm, MainActivity m) {
        String tmp = null;
        SearchResult result = getSearchResult(searchterm, m);
        //make sure the searchterm doesn't already exist in the list
        if (!searchListFound(searchterm)) {
            //check if the list is greater than 4; 0 is included
            if (suggestList.size() > 3) {
                suggestList.remove(0);
                suggestList.add(result);
            }
            //if not, add to list
            else {
                suggestList.add(result);
            }
        }
        return suggestList;
    }

    private boolean searchListFound(String searchterm) {
        for (int x = 0; x < suggestList.size(); x++) {
            if (suggestList.get(x).toString().equals(searchterm)) {
                return true;
            }
        }
        return false;
    }

    public SearchResult getSearchResult(String searchterm, MainActivity m) {
        if (searchterm != null) {
            return new SearchResult(searchterm, m.getResources().getDrawable(R.drawable.ic_action_history, null));
        }
        return null;
    }
}
