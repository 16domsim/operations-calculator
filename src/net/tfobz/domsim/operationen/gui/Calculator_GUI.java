package net.tfobz.domsim.operationen.gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.management.OperationsException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import net.tfobz.domsim.operationen.grundbausteine.*;
import net.tfobz.domsim.operationen.grundrechnungen.*;
import net.tfobz.domsim.operationen.erweiterterechnungen.*;
import net.tfobz.domsim.operationen.funktionen.*;
import net.tfobz.domsim.operationen.funktionen.Integer;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Calculator_GUI extends JFrame {

	private JPanel contentPane;
	private JTree operationenbaum = null;
	private JPopupMenu kontextmen = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculator_GUI frame = new Calculator_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Calculator_GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		MutableTreeNode o = new Addition(new Multiplikation(new Arccotangens(new Konstante(3.8)), new Konstante(1.0)),
				new Subtraktion(new Signum(new Konstante(8.5)), new Wurzel(new Konstante(2.5), new Argument(3))));

		TreeCellRenderer meinrenderer = new MeinTreeCellRenderer();
		TreeModel operationenmodel = new DefaultTreeModel(o);

		operationenbaum = new JTree(operationenmodel);
		operationenbaum.setRootVisible(true);
		operationenbaum.setEditable(true);
		operationenbaum.setCellRenderer(meinrenderer);

		operationenbaum.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isControlDown())
					kontextmen.show(contentPane, e.getX(), e.getY());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		kontextmen = new JPopupMenu();

		JMenu neu = new JMenu();
		neu.setText("Neu");

		JMenuItem loeschen = new JMenuItem();
		loeschen.setText("Löschen");
		loeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath().getLastPathComponent() != null
						&& !operationenbaum.getSelectionPath().getLastPathComponent().equals(treeModel.getRoot())) {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					treeModel.removeNodeFromParent(treeNode);
					TreePath treePath = operationenbaum.getSelectionPath();
					treeModel.reload();
					operationenbaum.expandPath(treePath);
				}
			}
		});

		JMenuItem vertauschen = new JMenuItem();
		vertauschen.setText("Vertauschen");
		vertauschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath().getLastPathComponent() != null) {
					if (operationenbaum.getSelectionPath().getLastPathComponent() instanceof Operation) {
						((Operation) operationenbaum.getSelectionPath().getLastPathComponent()).vertausche();
						TreePath treePath = operationenbaum.getSelectionPath();
						treeModel.reload();
						operationenbaum.expandPath(treePath);
					}
				}
			}
		});
		kontextmen.add(neu);
		kontextmen.add(loeschen);
		kontextmen.add(vertauschen);

		JMenu grundrechnungen = new JMenu();
		grundrechnungen.setText("Grundrechnungen");

		JMenu erweiterterechnungen = new JMenu();
		erweiterterechnungen.setText("Erweiterte Rechnungen");

		JMenu funktionen = new JMenu();
		funktionen.setText("Funktionen");

		neu.add(grundrechnungen);
		neu.add(erweiterterechnungen);
		neu.add(funktionen);

		JMenu trig = new JMenu();
		trig.setText("Trigonometrische");

		JMenu antitrig = new JMenu();
		antitrig.setText("Trigonometrische Umkehr");

		JMenu andere = new JMenu();
		andere.setText("Andere");

		funktionen.add(trig);
		funktionen.add(antitrig);
		funktionen.add(andere);

		JMenuItem konstante = new JMenuItem();
		konstante.setText("Konstante");
		konstante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Konstante(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Konstante(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Konstante(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem addition = new JMenuItem();
		addition.setText("Addition");
		addition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Addition(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Addition(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Konstante(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem subtraktion = new JMenuItem();
		subtraktion.setText("Subtraktion");
		subtraktion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Konstante(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Konstante(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Konstante(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem multiplikation = new JMenuItem();
		multiplikation.setText("Multiplikation");
		multiplikation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Konstante(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Konstante(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Konstante(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem division = new JMenuItem();
		division.setText("Division");
		division.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Konstante(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Konstante(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Konstante(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		grundrechnungen.add(konstante);
		grundrechnungen.addSeparator();
		grundrechnungen.add(addition);
		grundrechnungen.add(subtraktion);
		grundrechnungen.add(multiplikation);
		grundrechnungen.add(division);

		JMenuItem argument = new JMenuItem();
		argument.setText("Argument");
		argument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() != null) {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if (treeNode instanceof ArgOperation && treeNode.getChildCount() < 2) {
						boolean gueltig = true;
						if (treeNode.getChildCount() >= 1) {
							gueltig = false;
						}
						if (gueltig) {
							treeModel.insertNodeInto(new Argument(), treeNode, 0);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
					}
				}
			}
		});

		JMenuItem potenz = new JMenuItem();
		potenz.setText("Potenz");
		potenz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Konstante(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Konstante(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Konstante(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem wurzel = new JMenuItem();
		wurzel.setText("Wurzel");
		wurzel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Konstante(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Konstante(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Konstante(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem logarithmus = new JMenuItem();
		logarithmus.setText("Logarithmus");
		logarithmus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Konstante(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Konstante(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Konstante(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Konstante(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		erweiterterechnungen.add(argument);
		erweiterterechnungen.addSeparator();
		erweiterterechnungen.add(potenz);
		erweiterterechnungen.add(wurzel);
		erweiterterechnungen.add(logarithmus);

		JMenuItem sin = new JMenuItem();
		sin.setText("Sinus");
		sin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Sinus(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Sinus(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Sinus(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Sinus(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Sinus(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem cos = new JMenuItem();
		cos.setText("Cosinus");
		cos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Cosinus(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Cosinus(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Cosinus(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Cosinus(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Cosinus(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem tan = new JMenuItem();
		tan.setText("Tangens");
		tan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Tangens(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Tangens(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Tangens(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Tangens(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Tangens(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem cotan = new JMenuItem();
		cotan.setText("Cotangens");
		cotan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Cotangens(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Cotangens(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Cotangens(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Cotangens(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Cotangens(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		trig.add(sin);
		trig.add(cos);
		trig.add(tan);
		trig.add(cotan);

		JMenuItem asin = new JMenuItem();
		asin.setText("Arcus Sinus");
		asin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Arcsinus(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Arcsinus(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Arcsinus(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Arcsinus(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Arcsinus(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem acos = new JMenuItem();
		acos.setText("Arcus Cosinus");
		acos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Arccosinus(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Arccosinus(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Arccosinus(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Arccosinus(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Arccosinus(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem atan = new JMenuItem();
		atan.setText("Arcus Tangens");
		atan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Arctangens(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Arctangens(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Arctangens(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Arctangens(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Arctangens(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem acotan = new JMenuItem();
		acotan.setText("Arcus Cotangens");
		acotan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Arccotangens(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Arccotangens(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Arccotangens(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Arccotangens(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Arccotangens(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		antitrig.add(asin);
		antitrig.add(acos);
		antitrig.add(atan);
		antitrig.add(acotan);

		JMenuItem betrag = new JMenuItem();
		betrag.setText("Betrag");
		betrag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Betrag(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Betrag(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Betrag(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Betrag(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Betrag(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem integ = new JMenuItem();
		integ.setText("Integer");
		integ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Integer(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Integer(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Integer(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Integer(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Integer(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		JMenuItem sig = new JMenuItem();
		sig.setText("Signum");
		sig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel treeModel = (DefaultTreeModel) operationenbaum.getModel();
				if (operationenbaum.getSelectionPath() == null) {
					DefaultMutableTreeNode wurzel = (DefaultMutableTreeNode) treeModel.getRoot();
					if (wurzel.getChildCount() == 0)
						treeModel.insertNodeInto(new Signum(), wurzel, 0);
					else
						treeModel.insertNodeInto(new Signum(), wurzel, 1);
					treeModel.reload();
				} else {
					MutableTreeNode treeNode = (MutableTreeNode) operationenbaum.getSelectionPath()
							.getLastPathComponent();
					if ((treeNode instanceof Operation || treeNode instanceof ArgOperation
							|| treeNode instanceof Funktion) && treeNode.getChildCount() < 2) {
						if (treeNode instanceof Operation) {
							int index = 1;
							if (treeNode.getChildCount() < 1)
								index = 0;
							treeModel.insertNodeInto(new Signum(), treeNode, index);
							TreePath treePath = operationenbaum.getSelectionPath();
							treeModel.reload();
							operationenbaum.expandPath(treePath);
						}
						if (treeNode instanceof ArgOperation) {
							boolean gueltig = false;
							if (treeNode.getChildCount() >= 1)
								gueltig = true;
							if (gueltig) {
								treeModel.insertNodeInto(new Signum(), treeNode, 1);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}
						if (treeNode instanceof Funktion) {
							if (treeNode.getChildCount() == 0) {
								treeModel.insertNodeInto(new Signum(), treeNode, 0);
								TreePath treePath = operationenbaum.getSelectionPath();
								treeModel.reload();
								operationenbaum.expandPath(treePath);
							}
						}

					}
				}
			}
		});

		andere.add(betrag);
		andere.add(integ);
		andere.add(sig);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE).addContainerGap()));

		scrollPane.setViewportView(operationenbaum);
		scrollPane.add(kontextmen);
		contentPane.setLayout(gl_contentPane);
	}
}
