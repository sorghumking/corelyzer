/******************************************************************************
 *
 * CoreWall / Corelyzer - An Initial Core Description Tool
 * Copyright (C) 2004, 2005 Arun Gangadhar Gudur Rao, Julian Yu-Chung Chen
 * Electronic Visualization Laboratory, University of Illinois at Chicago
 *
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either Version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along
 * with this software; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Questions or comments about CoreWall should be directed to
 * cavern@evl.uic.edu
 *
 *****************************************************************************/

package corelyzer.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.chronos.util.j2k.J2KUtils;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import net.miginfocom.swing.MigLayout;

import corelyzer.data.ImagePropertyTable;
import corelyzer.data.ImagePropertyTableModel;
import corelyzer.data.Session;
import corelyzer.data.TrackSceneNode;
import corelyzer.data.coregraph.CoreGraph;
import corelyzer.util.FeedUtils;
import corelyzer.util.FileUtility;
import corelyzer.util.core.CoreModule;
import corelyzer.util.image.ImageModule;

public class CRLoadImageListingDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 627120465409891512L;

	public static void main(final String[] args) {
		CRLoadImageListingDialog dialog = new CRLoadImageListingDialog(null);
		dialog.pack();
		dialog.setVisible(true);
		System.exit(0);
	}

	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JButton filesButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton resetButton;
	private JButton helpButton;
	private ImagePropertyTable imageTable;
	private BatchInputPanel batchPanel;
	private JComboBox<TrackSceneNode> destTrackList;

	public CRLoadImageListingDialog(final Frame owner) {
		super(owner);
		$$$setupUI$$$();
		setTitle("Image Listing");

		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				onOK();
			}
		});

		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				onCancel();
			}
		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				onCancel();
			}
		});

		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		filesButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				onFiles();
			}
		});
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				selectAndLoadCSVFileToList(",");
			}
		});
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				saveTableToCSVFile(",");
			}
		});
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				onReset();
			}
		});

		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				onHelp();
			}
		});
	}
		
	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return contentPane;
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT
	 * edit this method OR call it in your code!
	 * 
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		createUIComponents();
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
		contentPane.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
		final Spacer spacer1 = new Spacer();
		panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
				GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel1.add(panel2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		buttonCancel = new JButton();
		buttonCancel.setText("Cancel");
		panel2.add(buttonCancel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		buttonOK = new JButton();
		buttonOK.setText("OK");
		panel2.add(buttonOK, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		helpButton = new JButton();
		helpButton.setText("Help");
		// panel1.add(helpButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
		// 		GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
		contentPane
				.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
						| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
						0, false));
		panel3.setBorder(BorderFactory.createTitledBorder("Image Files and Properties"));
		final JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JPanel panel5 = new JPanel();
		panel5.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel4.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final Spacer spacer2 = new Spacer();
		panel5.add(spacer2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1,
				GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(122, 14), null, 0, false));
		final JPanel panel6 = new JPanel();
		panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel5.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
				new Dimension(122, 33), null, 0, false));
		filesButton = new JButton();
		filesButton.setText("Select Images...");
		panel6.add(filesButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
				GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(120, 29), null, 0, false));
		final JPanel panel7 = new JPanel();
		panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel5.add(panel7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
				new Dimension(122, 33), null, 0, false));
		openButton = new JButton();
		openButton.setEnabled(true);
		openButton.setText("Open Image Listing...");
		panel7.add(openButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
				GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel8 = new JPanel();
		panel8.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel5.add(panel8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
				new Dimension(122, 33), null, 0, false));
		saveButton = new JButton();
		saveButton.setEnabled(false); // only enabled when list has at least one row
		saveButton.setText("Save Image Listing...");
		panel8.add(saveButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
				GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel9 = new JPanel();
		panel9.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel5.add(panel9, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
				new Dimension(122, 33), null, 0, false));
		resetButton = new JButton();
		resetButton.setText("Reset");
		panel9.add(resetButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
				GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel10 = new JPanel();
		panel10.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel4.add(panel10, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JScrollPane scrollPane1 = new JScrollPane();
		panel10.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK
						| GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		scrollPane1.setViewportView(imageTable);

		batchPanel = new BatchInputPanel(imageTable);
		panel3.add(batchPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		
		JPanel destTrackPanel = new JPanel(new MigLayout("fillx, insets 5", "[80%][20%]", "[]"));
		destTrackPanel.setBorder(BorderFactory.createTitledBorder("Destination Track"));
		destTrackPanel.add(new JLabel("Load images into track: "));
		destTrackList = new JComboBox<TrackSceneNode>();
		destTrackPanel.add(destTrackList, "growx, gapright 10, cell 0 0");
		updateTrackList();
		destTrackList.setSelectedItem(CorelyzerApp.getApp().getSelectedTrack());
		
		JButton newTrackButton = new JButton("Create Track");
		destTrackPanel.add(newTrackButton, "growx");
		newTrackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				createNewTrack();
			}
		});
		
		contentPane.add(destTrackPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK
				| GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
	}

	private void createUIComponents() {
		imageTable = new ImagePropertyTable();
	}
	
	private void updateTrackList() {
		Session curSession = CorelyzerApp.getApp().getSelectedSession();
		if (curSession != null) {
			DefaultComboBoxModel<TrackSceneNode> comboModel = new DefaultComboBoxModel<TrackSceneNode>();
			for (TrackSceneNode tsn : curSession.getTrackSceneNodes()) {
				comboModel.addElement(tsn);
			}
			destTrackList.setModel(comboModel);
		}
	}

	private void createNewTrack() {
		String trackName = JOptionPane.showInputDialog(this, "Please input a track name");
		if (trackName != null) {
			Session curSession = CorelyzerApp.getApp().getSelectedSession();
			final boolean trackExists = (curSession != null && curSession.getTrackSceneNodeWithName(trackName) != null);
			if (!trackExists || curSession == null)  { // creation of a track with no session creates Default session
				if (CorelyzerApp.getApp().getController().createTrack(trackName) >= 0) {
					updateTrackList();
					destTrackList.setSelectedIndex(destTrackList.getItemCount() - 1);
				}
			} else {
				JOptionPane.showMessageDialog(this, "The track '" + trackName + "' already exists.");
			}
		}
	}

	public void fillListWithFiles(final Vector<File> files) {
		ImagePropertyTable theTable = (ImagePropertyTable) imageTable;

		for (File file : files) {
			String s = file.getAbsolutePath();

			String ext = FileUtility.getExtension(file);
			if (ext.equalsIgnoreCase("jp2")) { // jpeg2000
				List<String> xmlPayloads = J2KUtils.getXMLPayloads(file);

				// FIXME
				if ((xmlPayloads == null) || (xmlPayloads.size() == 0)) {
					theTable.addImagePath(s);
					continue;
				}

				for (String xml : xmlPayloads) {
					// see if it's core section image spec related
					if (FeedUtils.isValidSyndEntry(xml)) {
						try {
							file.toURI().toURL().toString();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}

						float dpix = 254;
						float dpiy = 254;
						float depth = 0;
						float length = 1;
						String orientation = ImagePropertyTableModel.HORIZONTAL;

						CoreModule module1 = FeedUtils.getCoreModule(xml);
						if (module1 == null) {
							System.err.println(file + " has no CoreModule");
						} else {
							depth = (float) module1.getDepth();
							length = (float) module1.getLength();
						}

						ImageModule module2 = FeedUtils.getImageModule(xml);
						if (module2 == null) {
							System.err.println(file + " has no ImageModule");
						} else {
							dpix = (float) module2.getDPIX();
							dpiy = (float) module2.getDPIY();
							orientation = module2.getOrientation();
						}

						theTable.addImageAndProperties(s, orientation, length, dpix, dpiy, depth);

						System.out.println(s + " added");

						break;
					} else {
						System.out.println("Not feed entry");
					}
				}
			} else {
				theTable.addImagePath(s);
			}
		}

		imageTable.updateUI();
		
		if ( imageTable.getModel().getRowCount() > 0 )
			saveButton.setEnabled(true);
	}

	private void loadImagesWithProperties() {
		CorelyzerApp app = CorelyzerApp.getApp();
		TrackSceneNode destTrack = (TrackSceneNode)destTrackList.getSelectedItem();
		
		if (app != null && destTrack != null) {
			JProgressBar progress = app.getProgressUI();
			progress.setString("Loading Images");
			progress.setMaximum(imageTable.getRowCount());
			progress.setValue(0);
			// progress.setVisible(true);

			ImagePropertyTable theTable = (ImagePropertyTable) imageTable;

			for (int i = 0; i < imageTable.getRowCount(); i++) {
				String filepath = theTable.model.filepathVec.elementAt(i);
				String orientation = (String) theTable.model.getValueAt(i, 1);

				float length = (Float) theTable.model.getValueAt(i, 2);
				float dpix = (Float) theTable.model.getValueAt(i, 3);
				float dpiy = (Float) theTable.model.getValueAt(i, 4);
				float depth = (Float) theTable.model.getValueAt(i, 5);

				String fn = new File(filepath).getName();
				System.out.println("Loading image " + fn + " Orientation: " + orientation + " Length: " + length + " DPIX: " + dpix + " DPIY: " + dpiy
						+ " Depth:" + depth);

				progress.setValue(i + 1);
				progress.setString(fn);

				File f = new File(filepath);
				String url, sectionName = "";
				int nativeSectionId;
				try {
					// let's use filename only w/o extension
					String str = f.getName();
					int idx = str.lastIndexOf('.');
					str = str.substring(0, idx);
					sectionName = str;
					
					url = f.toURI().toURL().toString();
					nativeSectionId = app.loadImage(f, url, sectionName);
				} catch (MalformedURLException e) {
					nativeSectionId = -1;
					e.printStackTrace();
				}

				if (nativeSectionId < 0) {
					continue;
				}

				FileUtility.setSectionImageProperties(destTrack, sectionName, nativeSectionId, length, depth, dpix, dpiy, orientation);
			}

			progress.setValue(imageTable.getRowCount());
			progress.setString("All images loaded");
			progress.setValue(0);
			// progress.dispose();
		}
	}

	private void onCancel() {
		dispose();
	}

	private void onFiles() {
		selectAndLoadImagesToList();
	}

	private void onHelp() {
		WikiHelpDialog dialog = new WikiHelpDialog(this, "CRLoadImageDialog");
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private void onOK() {
		// create default session and track if necessary
		Session curSession = CoreGraph.getInstance().getCurrentSession();
		if (curSession.getNumberOfTracks() == 0) {
			JOptionPane.showMessageDialog(this, "A destination track must be selected.");
			return;
		}
		
		Runnable loading = new Runnable() {
			public void run() {
				loadImagesWithProperties();
			}
		};
		new Thread(loading).start();

		setVisible(false);
	}

	private void onReset() {
		((ImagePropertyTable) imageTable).clearTable();
		imageTable.updateUI();
		
		saveButton.setEnabled(false);
	}

	private void saveTableToCSVFile(final String delimiter) {
		// save current image list with each values to csv file
		// firt check if there is image file in the list
		int nFiles = imageTable.getRowCount();

		// 5/20/2012 brg: Save button now disables when the list is empty, leaving this
		// check in on the off-chance that logic can be subverted somehow...
		if (nFiles == 0) {
			JOptionPane.showMessageDialog(this, "Empty List!");
			return;
		}

		// show up general info message
		JOptionPane.showMessageDialog(this, "Save Image Listing File\n\n" + "only supports Comma Delimited Files (.csv)\n"
				+ "File will inlcude below values in each line.\n" + "filename, orientation, length, dpix, dpiy, depth");

		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Values (.csv)", "csv"));
		File selectedFile = null;
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			selectedFile = chooser.getSelectedFile();

		if (selectedFile == null) return;

		// make sure it has .csv at the end
		String path = selectedFile.getAbsolutePath();
		path = path.replace('\\', '/');
		String[] toks = path.split(delimiter);
		if (!toks[toks.length - 1].contains(".csv")) {
			path = path + ".csv";
			selectedFile = new File(path);
		}

		System.out.println("Saving to a CSV file..." + selectedFile);

		String filename, orientation;
		float length, dpix, dpiy, depth;

		try {
			String outLine;
			FileWriter fw = new FileWriter(selectedFile);
			BufferedWriter bw = new BufferedWriter(fw);

			ImagePropertyTable theTable = (ImagePropertyTable) imageTable;

			for (int i = 0; i < nFiles; i++) {
				// filename = (String) theTable.model.getValueAt(i, 0);
				filename = theTable.model.filepathVec.elementAt(i);
				orientation = (String) theTable.model.getValueAt(i, 1);
				length = (Float) theTable.model.getValueAt(i, 2);
				dpix = (Float) theTable.model.getValueAt(i, 3);
				dpiy = (Float) theTable.model.getValueAt(i, 4);
				depth = (Float) theTable.model.getValueAt(i, 5);

				// write property
				outLine = filename + "," + orientation + "," + length + "," + dpix + "," + dpiy + "," + depth + "\n";
				bw.write(outLine);
			}
			bw.flush();
			fw.flush();

			bw.close();
			fw.close();

			// show success message
			JOptionPane.showMessageDialog(this, "Saved successfully!");

		} catch (IOException e) {
			String mesg = "IOException in writing CSV file " + selectedFile;
			JOptionPane.showMessageDialog(this, mesg);

			e.printStackTrace();
		}
	}
	
	private int showContinueAbortMessage(String message, String title) {
		Object[] options = { "Continue", "Abort" };
		int response = JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		return response;
	}

	// load user processed csv file
	// each line inlcudes five tuples
	// filename, orientation, length, dpix, dpiy, depth
	private void selectAndLoadCSVFileToList(final String delimiter) {
		// show up general info message
		JOptionPane.showMessageDialog(this, "Image Listing Files must be in Comma Separated Values (.csv) format.\n\n"
				+ "- Each line must consist of six values:\n"
				+ "Filename (including extension), Orientation, Length (cm), DPI X, DPI Y, Depth (m)\n\n"
				+ "- Each filename must be either a complete path, or located in the same directory as the image listing file.\n"
				+ "- Orientation must be 'Horizontal' or 'Vertical' (excluding quotes).\n"
				+ "- Headers and comment lines, if included, must start with # to avoid errors.");

		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Values (.csv)", "csv"));
		File selectedFile = null;
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			selectedFile = chooser.getSelectedFile();

		if (selectedFile == null) return;

		String basePath = new File(selectedFile.getParent()).getAbsolutePath();

		// vars for each tuples
		String filepath, orientation;
		float length, dpix, dpiy, depth;

		try {
			FileReader fr = new FileReader(selectedFile);
			BufferedReader reader = new BufferedReader(fr);
			String line;
			String[] toks;
			int nLine = 1;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) continue; // skip header/comment lines
				toks = line.split(delimiter);
				if (toks.length != 6) {
					String errmsg = "Line format error!\n\n" + "Line does not have 6 separate values.\n" + 
									"File: " + selectedFile + "\n" + "Line: " + nLine + "\n" + line;
					if (showContinueAbortMessage(errmsg, "Error") == JOptionPane.NO_OPTION)
						break;
					nLine++;
					continue;
				}

				// check simple error
				boolean validated = true;
				for (int j = 0; j < 6; j++) {
					toks[j] = toks[j].trim();
					if (toks[j].length() == 0) { // missing token value!!!
						validated = false;
						break;
					}
				}

				if (!validated) {
					String errmsg = "Missing value found!\n\n" + "File: " + selectedFile + "\n" + "Line: " + nLine + "\n" + line;
					if (showContinueAbortMessage(errmsg, "Error") == JOptionPane.NO_OPTION)
						break;
					nLine++;
					continue;
				}

				File imageFile = new File(toks[0]);
				if (imageFile.exists()) {
					filepath = toks[0];
				} else { // check relative path
					String sp = System.getProperty("file.separator");
					String composedFullPath = basePath + sp + toks[0];

					imageFile = new File(composedFullPath);

					if (imageFile.exists()) {
						filepath = imageFile.getAbsolutePath();
					} else {
						String errmsg = "Image file '" + toks[0] + "' doesn't exist";
						if (showContinueAbortMessage(errmsg, "Error") == JOptionPane.NO_OPTION)
							break;
						continue;
					}
				}

				orientation = toks[1];
				length = Float.parseFloat(toks[2]);
				dpix = Float.parseFloat(toks[3]);
				dpiy = Float.parseFloat(toks[4]);
				depth = Float.parseFloat(toks[5]);

				((ImagePropertyTable) imageTable).addImageAndProperties(filepath, orientation, length, dpix, dpiy, depth);
				nLine++;
			}
			reader.close();
			fr.close();
		} catch (Exception e) {
			String mesg = "Image List File Parsing error";
			JOptionPane.showMessageDialog(this, mesg);
			System.err.println(mesg + ": " + e);
		}

		imageTable.updateUI();
		
		if ( imageTable.getModel().getRowCount() > 0 )
			saveButton.setEnabled(true);
	}

	private void selectAndLoadImagesToList() {
		final Vector<File> selectedFiles = FileUtility.loadLocalImages(this);
		
		filesButton.setEnabled(false);
		buttonOK.setEnabled(false);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				fillListWithFiles(selectedFiles);

				filesButton.setEnabled(true);
				buttonOK.setEnabled(true);
			}
		});
	}
}
