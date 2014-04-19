import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Models.HistoryModel;
import Models.LiveModel;

public class MainView extends JFrame {

	JTextField searchField;
	JTable table;
	JLabel medianValueLabel = new JLabel("Median: 0");

	private Controller controller;

	private int columnPadding = 10;

	public MainView(Controller historyController) {

		this.controller = historyController;

		JPanel queryPanel = new JPanel();
		queryPanel.setBackground(Color.lightGray);

		// Create vertical layout
		queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));
		JLabel searchLabel = new JLabel("Enter Search Text Below:");
		queryPanel.add(searchLabel);

		searchField = new JTextField(20);
		queryPanel.add(searchField);

		JButton searchButton = new JButton("Search");
		queryPanel.add(searchButton);
		searchButton.addActionListener(new SearchClickedListener());

		ButtonGroup viewRadioButtonGroup = new ButtonGroup();

		JRadioButton historyRadioButton = new JRadioButton();
		historyRadioButton.setText("Sold Items");
		viewRadioButtonGroup.add(historyRadioButton);
		historyRadioButton.setSelected(true);
		historyRadioButton.addActionListener(new HistorySelectedListener());

		JRadioButton liveRadioButton = new JRadioButton();
		liveRadioButton.setText("Live Items");
		liveRadioButton.addActionListener(new LiveSelectedListener());
		viewRadioButtonGroup.add(liveRadioButton);

		queryPanel.add(historyRadioButton);
		queryPanel.add(liveRadioButton);

		// Create table for results
		JPanel mainPanel = new JPanel();
		String[] columnNames = { "Item Name", "Item Price" };
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);
		model.setColumnIdentifiers(columnNames);

		JScrollPane scrollPane = new JScrollPane(table);
		mainPanel.add(scrollPane);
		mainPanel.add(medianValueLabel);

		this.getContentPane().add(BorderLayout.CENTER, mainPanel);
		this.getContentPane().add(BorderLayout.EAST, queryPanel);
		this.setSize(950, 600);
		this.setVisible(true);

	}

	/*
	 * Creating a table using an ArrayList of lists. Using a generic wildcard so
	 * that any type of list can be added. Down-side to this is we have to tell
	 * the compiler that what we're doing will be legal at execution time when
	 * casting to an explicit type as done for the call to setMedianValue. To
	 * ensure that it doesn't fail at runtime, an instanceof check is done.
	 */
	@SuppressWarnings("unchecked")
	public void updateTableValues(ArrayList<?> list) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		// Remove rows from previous query.
		tableModel.setRowCount(0);

		if (((List<?>) list.get(1)).get(1) instanceof Double) {
			setMedianValue((List<Double>) list.get(1));
		}

		for (int i = 0; i < ((List<?>) list.get(0)).size(); i++) {
			Object[] o = new Object[list.size()];
			for (int j = 0; j < list.size(); j++) {
				o[j] = ((List<?>) list.get(j)).get(i);
			}
			tableModel.addRow(o);
		}

		// Set column 0's width to the preferred size

		for (int col = 0; col < table.getColumnCount(); col++) {
			int width = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, col);
				Component comp = table.prepareRenderer(renderer, row, col);
				width = Math.max(comp.getPreferredSize().width, width);
			}
			table.getColumnModel().getColumn(col)
					.setPreferredWidth(width + columnPadding);
		}
	}

	private void setMedianValue(List<Double> list) {
		// Update Median Value
		Double priceArray[] = new Double[list.size()];
		priceArray = list.toArray(priceArray);
		java.util.Arrays.sort(priceArray);
		if (priceArray.length % 2 == 0) {
			medianValueLabel.setText("Median: "
					+ priceArray[(priceArray.length - 1) / 2]);
		} else {
			medianValueLabel
					.setText("Median: "
							+ (priceArray[priceArray.length / 2] + priceArray[(priceArray.length / 2) - 1])
							/ 2);
		}
	}

	class SearchClickedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (searchField.getText() != null
					&& !searchField.getText().trim().equals("")) {
				updateTableValues(controller.updateView(searchField.getText()));
			}
		}
	}

	class HistorySelectedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			HistoryModel historyModel = new HistoryModel();
			controller.setModel(historyModel);
		}
	}

	class LiveSelectedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			LiveModel liveModel = new LiveModel();
			controller.setModel(liveModel);
		}
	}

}
