package net.tfobz.domsim.operationen.grundbausteine;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;


public abstract class Operation extends Operand {
	private Operand[] operand = new Operand[2];

	public Operation(Operand operand0, Operand operand1) {
		this.setOperand(operand0);
		this.setOperand(operand1);
	}

	public Operation() {
		super();
	}

	public abstract String toString();

	public void setOperand(Operand operand) {
		operand.parent = this;
		if (this.operand[0] == null) {
			this.operand[0] = operand;
			this.operand[0].parent=this;
		}
		else if (this.operand[1] == null) {
			this.operand[1] = operand;
			this.operand[1].parent=this;
		}
	}

	public Operand getOperand(int position) {
		if (position >= 0 && position <= 1)
			return this.operand[position];
		else
			return null;
	}

	public void vertausche() {
		if (this.operand[0] != null && this.operand[1] != null) {
			Operand operand = this.operand[0];
			this.operand[0] = this.operand[1];
			this.operand[1] = operand;
		}
	}

	public void loescheOperand(int position) {
		if (position == 0) {
			this.operand[0] = this.operand[1];
			this.operand[1] = null;
		} else if (position == 1)
			this.operand[1] = null;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return this.getOperand(childIndex);
	}

	@Override
	public int getChildCount() {
		if (this.operand[0] != null && this.operand[1] != null)
			return 2;
		if (this.operand[0] != null || this.operand[1] != null)
			return 1;
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		if (node != null) {
			if (node.equals(this.operand[0]))
				return 0;
			if (node.equals(this.operand[1]))
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
					if (Operation.this.operand[0] != null)
						return true;
				}
				if (indexaktuelleselement == 0) {
					if (Operation.this.operand[1] != null)
						return true;
				}
				return false;
			}

			@Override
			public TreeNode nextElement() {
				indexaktuelleselement++;
				if (indexaktuelleselement > 1)
					indexaktuelleselement = 0;
				return Operation.this.operand[indexaktuelleselement];
			}

		};
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
		if (child instanceof Operand && child != null) {
			if (index == 0) 
				Operation.this.setOperand((Operand) child);
			if (index == 1 && Operation.this.operand[0] != null) {
				Operation.this.setOperand((Operand) child);
			}
		}
	}

	@Override
	public void remove(int index) {
		Operation.this.loescheOperand(index);
	}

	@Override
	public void remove(MutableTreeNode node) {
		if (node != null && node instanceof Operand) {
			if (node.equals(Operation.this.operand[0]))
				Operation.this.loescheOperand(0);
			if (node.equals(Operation.this.operand[1]))
				Operation.this.loescheOperand(1);
		}

	}

	@Override
	public void setUserObject(Object object) {
	}
}
