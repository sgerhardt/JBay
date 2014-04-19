import java.util.ArrayList;
import java.util.List;

import Models.EbayCommonModel;
import com.ebay.services.finding.SearchItem;

public class Controller {

	/* The view sends the search string to the controller */
	String searchString = "";

	private EbayCommonModel model;

	public Controller(EbayCommonModel model) {
		this.model = model;
		new MainView(this);
	}

	public void setSearchString(String searchStr) {
		model.setSearchString(searchStr);
	}

	public List<SearchItem> getSearchResults() {
		return model.getResults();
	}

	public List<Double> getItemPrices(List<SearchItem> items) {
		return model.getItemPrices(items);
	}

	public List<String> getItemTitles(List<SearchItem> items) {
		return model.getItemTitles(items);
	}

	public ArrayList<?> updateView() {
		model.setSearchString(searchString);
		List<SearchItem> list = this.getSearchResults();
		ArrayList<List> arrayOfLists = new ArrayList<List>();
		arrayOfLists.add(model.getItemTitles(list));
		arrayOfLists.add(model.getItemPrices(list));
		return arrayOfLists;
	}

	public void setModel(EbayCommonModel model) {
		this.model = model;
	}

}
