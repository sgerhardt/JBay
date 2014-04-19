import Models.HistoryModel;

public class JBayApp {

	public static void main(String args[]) {
		// Setup eBay API client
		HistoryModel model = new HistoryModel();
		//Start the app with the HistoryModel
		Controller controller = new Controller(model);

	}

}
