package net.tfobz.domsim.operationen.erweiterterechnungen;

import net.tfobz.domsim.operationen.grundbausteine.ArgOperation;
import net.tfobz.domsim.operationen.grundbausteine.Argument;
import net.tfobz.domsim.operationen.grundbausteine.Operand;

public class Logarithmus extends ArgOperation {

	public Logarithmus(Operand operand, Argument argument) {
		super(operand, argument);
	}

	public Logarithmus() {
		super();
	}

	@Override
	public double getErgebnis() {
		double ret = 0;
		if (this.getOperand() != null)
			ret = this.getOperand().getErgebnis();
		if (this.getArgument() != null) {
			if (ret > 0 && this.getArgument().getErgebnis() > 1)
				ret = Math.log(ret) / Math.log(this.getArgument().getErgebnis());
			else
				ret = 0;
		}
		return ret;
	}

	@Override
	public String toString() {
		String ret = "Not avaiable yet";
		if (this.getOperand() != null && this.getArgument() != null) {
			ret = "(log(" + this.getArgument().toString() + "," + this.getOperand().toString() + ")="
					+ String.valueOf(this.getErgebnis()) + ")";
		}
		return ret;
	}
}
