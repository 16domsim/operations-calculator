package net.tfobz.domsim.operationen.grundbausteine;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public abstract class ArgOperation extends Operand {

	private Operand operand;
	private Argument argument;

	public ArgOperation(Operand operand, Argument argument) {
		this.setOperand(operand);
		this.setArgument(argument);
	}

	public ArgOperation() {
		super();
	}

	public abstract String toString();

	public void setOperand(Operand operand) {
		if (this.operand == null) {
			this.operand = operand;
			this.operand.parent=this;
		}
	}

	public Operand getOperand() {
		return this.operand;
	}

	public void setArgument(Argument argument) {
		if (this.argument == null) {
			this.argument = argument;
			this.argument.parent = this;
		}
	}

	public Operand getArgument() {
		return this.argument;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		if (childIndex == 0)
			return this.getArgument();
		if (childIndex == 1)
			return this.getOperand();
		return null;
	}

	@Override
	public int getChildCount() {
		if (this.argument != null && this.operand != null)
			return 2;
		if (this.argument != null || this.operand != null)
			return 1;
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		if (node != null) {
			if (node.equals(this.argument))
				return 0;
			if (node.equals(this.operand))
				return 1;
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

			private int indexaktuelleselement = -1;

			@Override
			public boolean hasMoreElements() {
				if (indexaktuelleselement == -1) {
					if (ArgOperation.this.argument != null)
						return true;
				}
				if (indexaktuelleselement == 1)
					return false;
				if (indexaktuelleselement == 0) {
					if (ArgOperation.this.operand != null)
						return true;
				}
				return false;
			}

			@Override
			public TreeNode nextElement() {
				indexaktuelleselement++;
				if (indexaktuelleselement > 1) {
					indexaktuelleselement = 0;
					return ArgOperation.this.argument;
				}
				return ArgOperation.this.operand;
			}
		};
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
		if (child instanceof Operand && child != null) {
			if (index == 0)
				ArgOperation.this.setArgument((Argument) child);
			if (index == 1 && ArgOperation.this.argument != null)
				ArgOperation.this.setOperand((Operand) child);
		}
	}

	@Override
	public void remove(int index) {
		if (index == 0)
			ArgOperation.this.argument = null;
		if (index == 1)
			ArgOperation.this.operand = null;
	}

	@Override
	public void remove(MutableTreeNode node) {
		if (node != null && node instanceof Operand) {
			if (node.equals(ArgOperation.this.argument))
				ArgOperation.this.argument = null;
			if (node.equals(ArgOperation.this.operand))
				ArgOperation.this.operand = null;
		}

	}

	@Override
	public void setUserObject(Object object) {
	}
}