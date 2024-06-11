package net.tfobz.domsim.operationen.gui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import net.tfobz.domsim.operationen.erweiterterechnungen.Logarithmus;
import net.tfobz.domsim.operationen.erweiterterechnungen.Potenz;
import net.tfobz.domsim.operationen.erweiterterechnungen.Wurzel;
import net.tfobz.domsim.operationen.grundbausteine.Argument;
import net.tfobz.domsim.operationen.grundbausteine.Funktion;
import net.tfobz.domsim.operationen.grundbausteine.Konstante;
import net.tfobz.domsim.operationen.grundrechnungen.Addition;
import net.tfobz.domsim.operationen.grundrechnungen.Division;
import net.tfobz.domsim.operationen.grundrechnungen.Multiplikation;
import net.tfobz.domsim.operationen.grundrechnungen.Subtraktion;

public class MeinTreeCellRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		Component ret = null;
		ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		if(value instanceof Konstante) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Konstante.png")));
			
		}
		if(value instanceof Addition) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Addition.png")));
			
		}
		if(value instanceof Subtraktion) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Subtraktion.png")));
			
		}
		if(value instanceof Multiplikation) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Multiplikation.png")));
			
		}
		if(value instanceof Division) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Division.png")));
			
		}
		if(value instanceof Logarithmus) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Logarithmus.png")));
			
		}
		if(value instanceof Wurzel) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Wurzel.png")));
			
		}
		if(value instanceof Potenz) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Potenz.png")));
			
		}
		if(value instanceof Funktion) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Funktion.png")));
			
		}
		if(value instanceof Argument) {
			setIcon(new ImageIcon(MeinTreeCellRenderer.class.getResource("Argument.png")));
		}
		return ret;
	}
}
