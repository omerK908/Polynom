package Ex1;


public class ComplexFunction implements complex_function {

	private function left;
	private function right;
	private Operation p;
	
	public ComplexFunction() {
		left = null;
		right = null;
		p = Operation.None;
	}

	public ComplexFunction(function f) {
		this.left = f;
		this.right = null;
		this.p = Operation.None;
	}
	
	public ComplexFunction(Operation p , function f1, function f2) {
		if(f2 != null) {
			this.p = p;
		}
		else {
			this.p = Operation.None;
		}
		this.left = f1;
		this.right = f2;
	}

	public ComplexFunction(String str, function p1, function p2) {
		if(p2 != null) {
			this.p = fromStringToOperator(str);			
		}
		else if(p2 == null) {
			this.p=Operation.None;
		}
		this.left = p1;
		this.right = p2;
	}

	public double f(double x) {
		//	Plus, Times, Divid, Max, Min, Comp , None, Error
		double ans = 0;
		
		switch (p) {
		case Plus:
			ans =  this.left.f(x) + this.right.f(x);
			break;

		case Times:
			ans = this.left.f(x) * this.right.f(x);
			break;

		case Divid:
			if(this.right.f(x) != 0) ans =  this.left.f(x)/this.right.f(x);
			else throw new IllegalArgumentException("Cannot divide by 0!");
			break;

		case Max:
			ans = Math.max(this.left.f(x), this.right.f(x));
			break;

		case Min:
			ans = Math.min(this.left.f(x), this.right.f(x));
			break;

		case Comp:
			ans= this.left.f(this.right.f(x));
			break;

		case None:
			ans= this.left.f(x);
			break;
			
		case Error:
			throw new IllegalArgumentException("Wrong value!");
		
		default:
			throw new IllegalArgumentException("The string is wrong!");
		}	
		return ans;

	}

	private Operation fromStringToOperator(String operator) {
		String s = operator.toLowerCase();
		
		switch(s){
		
		case "plus":
			return Operation.Plus;

		case "div":
			return Operation.Divid;
			
		case "divid":
			return Operation.Divid;
			
		case "mul":
			return Operation.Times;
			
		case "times":
			return Operation.Times;
			
		case "max":
			return Operation.Max;

		case "min":
			return Operation.Min;

		case "comp":
			return Operation.Comp;

		case "none":
			return Operation.None;
			
		default:
			throw new RuntimeException("bad operation");
		}
	}

	public function initFromString(String s) {
		if(!isBalanced(s))
			throw new IllegalArgumentException("The delimiters are incorrect");

		if(s.indexOf('(')==-1) {
			 function f=new Polynom(s);
			 return f;
		}
		int ind1 = s.indexOf('(');
		int ind2 = s.indexOf(',');
		int indLast2=s.lastIndexOf(','); 
		int ind3 = s.lastIndexOf(')');
		
		String rig="",lef="";
		
		if(s.charAt(ind2+2)<='z' && s.charAt(ind2+2)>='A' && s.charAt(ind2+2)!='x') {
			rig = s.substring((ind2+2), ind3);
			lef = s.substring(ind1+1, ind2);
		}
		else {
			rig= s.substring(indLast2+2,ind3);
			lef = s.substring(ind1+1,indLast2);
		}
		String oper = s.substring(0,ind1);
		
		function p1=initFromString(lef);
		function p2=initFromString(rig);
		return new ComplexFunction(oper,p1,p2);		
	}

	public function copy() {
		function tmp = new ComplexFunction(this.p, this.left, this.right);
		return tmp;
	}

	public void plus(function f1) {
		makeComplex(f1);
		this.p=Operation.Plus;
	}

	public void mul(function f1) {
		makeComplex(f1);
		this.p=Operation.Times;			
	}

	public void div(function f1) {
		makeComplex(f1);
		this.p=Operation.Divid;
	}

	public void max(function f1) {
		makeComplex(f1);
		this.p=Operation.Max;
	}

	public void min(function f1) {
		makeComplex(f1);
		this.p=Operation.Min;
	}

	public void comp(function f1) {
		makeComplex(f1);
		this.p=Operation.Comp;
	}

	public function left() {
		return this.left;
	}

	public function right() {
		return this.right;
	}

	public Operation getOp() {
		return this.p;
	}

	public String toString() {
		StringBuilder ans = new StringBuilder();
		ans.append(this.p+"("+this.left.toString()+" , "+this.right.toString()+")");
		return ans.toString();
	}

	/*
	 * Times(2x,x)==plus(x^2,x^2)
	 * 
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof ComplexFunction))
			return false;

		ComplexFunction other = (ComplexFunction) obj;

		for (int i = 1; i <=10; i++) {
			if(this.f(i)!=other.f(i))
				return false;
		}
		return true;
	}
	//// private methods ////
	
	private void makeComplex(function f1) {
		ComplexFunction tmp = new ComplexFunction(this.p,this.left,this.right);
		this.left=tmp;
		this.right=f1;		
	}
	
	private boolean isBalanced(String s) {
		int counter = 0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '(') counter++;
			else if(s.charAt(i) == ')') counter--;
			if(counter < 0) return false;
		}
		return counter == 0;
	}
}




