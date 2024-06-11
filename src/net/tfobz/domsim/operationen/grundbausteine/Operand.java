package net.tfobz.domsim.operationen.grundbausteine;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public abstract class Operand implements MutableTreeNode {
	protected TreeNode parent;

	public abstract double getErgebnis();

	public abstract String toString();

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public void removeFromParent() {
		this.parent = null;

	}

	@Override
	public void setParent(MutableTreeNode newParent) {
		this.parent = newParent;
	}
}
