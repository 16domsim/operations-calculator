package net.tfobz.domsim.operationen.grundbausteine;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class Konstante extends Operand {
	private double ergebnis = 0.0;

	public Konstante(double ergebnis) {
		this.ergebnis = ergebnis;
	}

	public Konstante() {
		super();
	}

	public void setErgebnis(double ergebnis) {
		this.ergebnis = ergebnis;
	}

	public double getErgebnis() {
		return this.ergebnis;
	}

	@Override
	public String toString() {
		return String.valueOf(this.ergebnis);
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Enumeration<? extends TreeNode> children() {
		return null;
	}

	@Override
	public void insert(MutableTreeNode child, int index) {

	}

	@Override
	public void remove(int index) {

	}

	@Override
	public void remove(MutableTreeNode node) {

	}

	@Override
	public void setUserObject(Object object) {
		if (object != null && object instanceof String) {
			try {
				Konstante.this.ergebnis = Double.valueOf((String) object);
			} catch (NumberFormatException e) {
				System.out.println("Üngültige eingabe!");
			}
		}
	}
}
