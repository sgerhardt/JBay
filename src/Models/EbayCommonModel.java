package Models;

import java.util.List;

import com.ebay.services.finding.SearchItem;

abstract public class EbayCommonModel {

	abstract public void setSearchString(String searchStr);

	abstract public List<SearchItem> getResults();

	abstract public int getResultCount();

	abstract public List<String> getItemTitles(List<SearchItem> items);

	abstract public List<Double> getItemPrices(List<SearchItem> items);

	String applicationId = "Enter your eBay API key here...";

}
