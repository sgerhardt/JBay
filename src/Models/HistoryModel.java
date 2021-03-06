package Models;

import java.util.LinkedList;
import java.util.List;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.AckValue;
import com.ebay.services.finding.FindCompletedItemsRequest;
import com.ebay.services.finding.FindCompletedItemsResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;

public class HistoryModel extends EbayCommonModel {
	FindingServicePortType serviceClient;
	FindCompletedItemsRequest request;
	PaginationInput pi;
	FindCompletedItemsResponse result;
	List<SearchItem> items;

	public HistoryModel() {
		// initialize service end-point configuration
		ClientConfig config = new ClientConfig();
		config.setApplicationId(applicationId);
		// create a service client
		serviceClient = FindingServiceClientFactory.getServiceClient(config);
		// create request object
		request = new FindCompletedItemsRequest();
		pi = new PaginationInput();
		pi.setEntriesPerPage(50);
		request.setPaginationInput(pi);
	}

	/**
	 * Sets the search String.
	 * 
	 */
	public void setSearchString(String searchStr) {
		request.setKeywords(searchStr);
		request.setPaginationInput(pi);
	}

	/**
	 * Gets the list returned from eBay.
	 * 
	 */
	public List<SearchItem> getResults() {
		// call service
		result = serviceClient.findCompletedItems(request);
		items = result.getSearchResult().getItem();
		return items;
	}

	/**
	 * Gets the value of the count property.
	 * 
	 */
	public int getResultCount() {
		return result.getSearchResult().getCount();
	}

	/**
	 * Gets the item prices.
	 * 
	 */
	public List<Double> getItemPrices(List<SearchItem> items) {
		List<Double> prices = new LinkedList<Double>();
		for (SearchItem item : items) {
			prices.add(item.getSellingStatus().getCurrentPrice().getValue());
		}
		return prices;
	}

	/**
	 * Gets the item titles.
	 * 
	 */
	public List<String> getItemTitles(List<SearchItem> items) {
		List<String> titles = new LinkedList<String>();
		for (SearchItem item : items) {
			titles.add(item.getTitle());
		}
		return titles;
	}

	/**
	 * Gets the AckValue.
	 * 
	 */
	public AckValue getAck() {
		return result.getAck();
	}

}
