import java.util.ArrayList;
import java.util.List;

import Models.EbayCommonModel;
import com.ebay.services.finding.SearchItem;

public class Controller {

	private EbayCommonModel model;

	public Controller(EbayCommonModel model) {
		this.model = model;
		new MainView(this);
	}

	public List<SearchItem> getSearchResults() {
		return model.getResults();
	}

	public ArrayList<?> updateView(String searchString) {
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
