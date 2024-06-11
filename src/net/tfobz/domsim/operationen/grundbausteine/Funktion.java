package net.tfobz.domsim.operationen.grundbausteine;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public abstract class Funktion extends Operand {

	private Operand operand;

	public Funktion(Operand operand) {
		this.setOperand(operand);
	}

	public Funktion() {
		super();
	}

	public abstract String toString();

	public void setOperand(Operand operand) {
		if (this.operand == null) {
			this.operand = operand;
			this.operand.parent = this;
		}
	}

	public Operand getOperand() {
		return this.operand;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		if (childIndex == 0)
			return this.operand;
		return null;
	}

	@Override
	public int getChildCount() {
		if (this.operand != null)
			return 1;
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		if (node != null) {
			if (node.equals(this.operand))
				return 0;
		}
		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Enumeration<TreeNode> children() {
		return new Enumeration<TreeNode>() {

			@Override
			public boolean hasMoreElements() {
				return false;
			}

			@Override
			public TreeNode nextElement() {
				return Funktion.this.operand;
			}
		};
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
		if (child instanceof Operand && child != null && index == 0)
			Funktion.this.setOperand((Operand) child);
	}

	@Override
	public void remove(int index) {
		if (index == 0)
			Funktion.this.operand = null;
	}

	@Override
	public void remove(MutableTreeNode node) {
		if (node != null && node instanceof Operand) {
			if (node.equals(Funktion.this.operand))
				Funktion.this.operand = null;
		}
	}

	@Override
	public void setUserObject(Object object) {
	}
}
