package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
//import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
//import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	public Struct boolType = new Struct(Struct.Bool);
	//public Struct setType = new Struct(Struct.Enum);
	
	Logger log = Logger.getLogger(getClass());
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}

	private int mainPc;
	
	public int getMainPc(){
		return mainPc;
	}
	
	public void visit(PrintStatement printStatement){
		
		//ord
		Expr expr = printStatement.getExpr();
		Term term = null;
		if(expr instanceof TermExpression) {
			term = ((TermExpression) expr).getTerm();
		}
		if(expr instanceof MinusTermExpression) {
			term = ((MinusTermExpression) expr).getTerm();
		}
		Factor factor = term.getFactor();
		boolean isOrd = false;
		if(factor instanceof DesignatorFactor) {
			if(((DesignatorFactor) factor).getDesignator() instanceof Designator1) {
				String designatorName = ((Designator1)((DesignatorFactor) factor).getDesignator()).getDesignatorName();
				//report_info("designatorName: " + designatorName, printStatement);
				if(designatorName.equals("ord")) {
					isOrd = true;
				}
			}
			if(((DesignatorFactor) factor).getDesignator() instanceof DesignatorElemOfArray) {
				String designatorName = ((DesignatorElemOfArray)((DesignatorFactor) factor).getDesignator()).getDesignatorName();
				//report_info("designatorName: " + designatorName, printStatement);
				if(designatorName.equals("ord")) {
					isOrd = true;
				}
			}
		}
		//kraj ord
		
		if(printStatement.getExpr().struct == Tab.intType || printStatement.getExpr().struct.equals(boolType)){
			Code.loadConst(5);
			Code.put(Code.print);
		} else if(isOrd) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else if (printStatement.getExpr().struct.equals(SemanticPass.setType)) {
			printSet();
		} else {	
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(ConstTypeNumber constTypeNumber) {
	    //report_info("Numeric constant with value: " + constTypeNumber.getN1(), constTypeNumber);
	    
	    Obj con = null;
	    if(constTypeNumber.getParent() instanceof ConstDecl) {
	    	con = Tab.insert(Obj.Con, "$"+((ConstDecl)constTypeNumber.getParent()).getConstName(), Tab.intType);
	    }
	    if(constTypeNumber.getParent() instanceof MoreConstsList1) {
	    	con = Tab.insert(Obj.Con, "$"+((MoreConstsList1)constTypeNumber.getParent()).getConstName(), Tab.intType);
	    }
	    
	    con.setLevel(0);
	    //con.setAdr(constTypeNumber.getN1());
	    Code.load(con); // Load the constant value
	}
	
	public void visit(ConstDecl constDecl) {
		//report_info("Usao u constDecl " + constDecl.getConstName(), constDecl);
		
		
		Obj constDeclObj = Tab.find(constDecl.getConstName());
		//Obj constDeclObj = constDecl.obj;
		
		Code.store(constDeclObj);
		
		
	}
	
	public void visit(MoreConstsList1 moreConstsList1) {
		report_info("Usao u moreConstsList1 " + moreConstsList1.getConstName(), moreConstsList1);
		
		
		Obj constDeclObj = Tab.find(moreConstsList1.getConstName());
		//Obj constDeclObj = constDecl.obj;
		
		Code.store(constDeclObj);
		
		
	}
	
	public void visit(NumFactor numFactor){
		//report_info("Usao u numFaktor", numFactor);
		
		Obj con = Tab.insert(Obj.Con, "$", numFactor.struct);
		con.setLevel(0);
		con.setAdr(numFactor.getValue());
		
		Code.load(con);
		
		if((numFactor.getParent().getParent() instanceof MinusTermExpression)){
			Code.put(Code.neg);
		}
	}
	
	public void visit(MinusTermExpression minusTermExpression) {
		//report_info("Usao u minusTermExpression", minusTermExpression);
	}
	
	public void visit(DesignatorFactor designatorFactor){
		//report_info("Usao u DesignatorFactor", designatorFactor);
		//report_info("parent instanceof minustermexpr: " + (designatorFactor.getParent().getParent() instanceof MinusTermExpression), designatorFactor);
		
		if((designatorFactor.getParent().getParent() instanceof MinusTermExpression)){
			Code.put(Code.neg);
		}
	}
	
	public void visit(ExprFactor exprFactor){
		//report_info("Usao u numFaktor", numFactor);
		
		if((exprFactor.getParent().getParent() instanceof MinusTermExpression)){
			Code.put(Code.neg);
		}
		
	}	
	
	public void visit(CharFactor charFactor){
		//report_info("Usao u charFactor " + charFactor.value, charFactor);
		
		char charValue = charFactor.getValue();

	    // Create a new constant object for the character (char value is represented as an integer)
	    Obj con = Tab.insert(Obj.Con, "$char", Tab.charType);
	    con.setLevel(0);
	    con.setAdr((int) charValue); // Store the ASCII value of the character

	    // Load the constant value (the character is treated as an integer)
	    Code.load(con);
	}
	
	public void visit(BoolFactor boolFactor){
		//report_info("Usao u boolFactor " + boolFactor.getValue(), boolFactor);
		
		int boolValue = 0;
		if(boolFactor.getValue().equals("true")) {
			boolValue = 1;
		}
		
		//report_info("boolValue " + boolValue, null);
		
	    // Create a new constant object for the character (char value is represented as an integer)
	    Obj con = Tab.insert(Obj.Con, "$bool", boolType);
	    con.setLevel(0);
	    con.setAdr(boolValue); // Store the ASCII value of the character

	    // Load the constant value (the character is treated as an integer)
	    Code.load(con);
	}
	
	public void visit(VoidMethodSignature voidMethodSignature){
		
		if("main".equalsIgnoreCase(voidMethodSignature.getMethodName())){
			mainPc = Code.pc;
		}
		
		//moje dodato
		voidMethodSignature.obj = Tab.find(voidMethodSignature.getMethodName());
		
		voidMethodSignature.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = voidMethodSignature.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	
	}
	
	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(DesignatorStatementAssignopExpression designatorStatementAssignopExpression){
		//report_info("Usao u designatorStatementAssignopExpression", designatorStatementAssignopExpression);
		
		Designator designator = designatorStatementAssignopExpression.getDesignator();
		
		Obj designatorObj = designator.obj;
		
		if(designator instanceof Designator1) {
			Code.store(designatorObj);	
			
			//bugfix
//			if(charArray) {
//				
//				
//				charArray = false;
//			}
			
		} else {
			//report_info("pozvao astore", designatorStatementAssignopExpression);
			
			Code.put(Code.astore);			
		}
		
	}
	
	public void visit(Designator1 designator){
		//report_info("Usao u Designator1 " + designator.getDesignatorName(), designator);
		
		SyntaxNode parent = designator.getParent();
		
		Obj designatorObj = designator.obj;

		if(!designator.getDesignatorName().equals("ord") && !designator.getDesignatorName().equals("add") && !designator.getDesignatorName().equals("addAll")) {
			Code.load(designatorObj);
		}			
		
	}
	
	public void visit(DesignatorElemOfArray designator){
		//report_info("Usao u DesignatorElemOfArray", designator);
		
		SyntaxNode parent = designator.getParent();
		Obj designatorObj = designator.obj;
															
		
		if (parent instanceof DesignatorStatementAssignopExpression) {
			// The stack initially has: [index, array_reference]
		    // Reverse the top two elements: [array_reference, index]
				
			Code.load(designatorObj);
			
		    //Code.put(Code.dup);      // Duplicate the top element (index) -> [index, array_reference, index]
		    //Code.put(Code.pop);      // Remove the old top (index) -> [array_reference, index]
		    Code.put(Code.dup_x1);     // Duplicate the top two elements -> [array_reference, index, array_reference, index]
		    Code.put(Code.pop);      // Remove the old duplicate -> [index, array_reference]	
	    } else {
	    	Code.load(designatorObj);
			
			// The stack initially has: [index, array_reference]
		    // Reverse the top two elements: [array_reference, index]

		    //Code.put(Code.dup);      // Duplicate the top element (index) -> [index, array_reference, index]
		    //Code.put(Code.pop);      // Remove the old top (index) -> [array_reference, index]
		    Code.put(Code.dup_x1);     // Duplicate the top two elements -> [array_reference, index, array_reference, index]
		    Code.put(Code.pop);      // Remove the old duplicate -> [index, array_reference]
	    	
	        // Load a value from the array
	        Code.put(Code.aload);
	        
	    }
		
	}
	
	public void visit(AddopTermList1 addopTermList1){
		//report_info("Usao u addopTermList1", addopTermList1);
		
		Addop addop = addopTermList1.getAddop();
	    if (addop instanceof AddopPlus) {
	        Code.put(Code.add);
	    } else if (addop instanceof AddopMinus) {
	        Code.put(Code.sub);
	    }
		
	}
	
	public void visit(MulopFactorList1 mulopFactorList1){
		//report_info("Usao u addopTermList1", addopTermList1);
		
		Mulop mulop = mulopFactorList1.getMulop();
	    if (mulop instanceof MulopMul) {
	        Code.put(Code.mul);
	    } else if (mulop instanceof MulopDiv) {
	        Code.put(Code.div);
	    } else if(mulop instanceof MulopMod) {
	    	Code.put(Code.rem);
	    }
		
	}
	
	
	public void visit(DesignatorStatementPlusPlus designatorStatementPlusPlus) {
		Obj designatorObj = designatorStatementPlusPlus.getDesignator().obj;
		
		Code.put(Code.inc);
        Code.put(designatorObj.getAdr());
        Code.put(1);
	}
	
	public void visit(DesignatorStatementMinusMinus designatorStatementMinusMinus) {
		Obj designatorObj = designatorStatementMinusMinus.getDesignator().obj;
		
		Code.put(Code.inc);
        Code.put(designatorObj.getAdr());
        Code.put(-1);
	}
	
	public void visit(ReadStatement readStatement) {
		Obj designatorObj = readStatement.getDesignator().obj;
		
		if(designatorObj.getType() == Tab.intType){
			Code.loadConst(5);
			Code.put(Code.read);
		}else{
			Code.loadConst(1);
			Code.put(Code.bread);
		}
		
		Code.store(designatorObj);
	}
	
	public boolean charArray = false;
	public void visit(NewTypeFactor newTypeFactor) {
	    // Report entering the visit method
	    //report_info("Usao u newTypeFactor " + newTypeFactor.getType().getTypeName(), newTypeFactor);
	    //report_info("Type equals setType: " + newTypeFactor.getType().struct.equals(SemanticPass.setType), newTypeFactor);

	    // Check the type of the array being created
	    Struct elementType = newTypeFactor.getType().struct;

	    if (elementType == Tab.charType) {
	        // char or bool types use byte allocation
	        Code.put(Code.newarray);
	        
	        //bugfix, bilo je 0 ranije
	        Code.put(1); // 0 indicates byte allocation
	        
	        charArray = true;
	    } else if (elementType == Tab.intType) {
	        // int type uses word allocation
	        Code.put(Code.newarray);
	        Code.put(1); // 1 indicates word allocation
	    } else if (newTypeFactor.getType().getTypeName().equals("set")) {
	        // set type uses word allocation
	    	//report_info("newarray setType ", newTypeFactor);
	    	
	        Code.put(Code.newarray);
	        Code.put(1); // 1 indicates word allocation
	    } 
	    
	}

	
	
	public void visit(Function function) {
		Designator1 designator = (Designator1) function.getDesignator();

		if(designator.getDesignatorName().equals("add")) {		
			//report_info("Usao u add ", function);
			
			add();
			
		}
		
		if(designator.getDesignatorName().equals("addAll")) {		
			//report_info("Usao u addAll ", function);
			
			addAll();
			
		}
		
		
	}
	
	public void add() {
		//stack state: 
		//array
		//item
		
		//storing elements to local variables
		Code.store(SemanticPass.dummy1); //item
		Code.store(SemanticPass.dummySet1); //array
		Code.loadConst(0);
		Code.store(SemanticPass.dummy2); //index
		
		int loopStart = Code.pc;
		
//		Code.loadConst(42); // usao u petlju
//		Code.loadConst(5);
//		Code.put(Code.print);
		
		// check if element of array is 0
		Code.load(SemanticPass.dummySet1); //array
		Code.load(SemanticPass.dummy2); //index
		Code.put(Code.aload);
		Code.loadConst(0);
		
		// if element is 0 jump to end
		Code.putFalseJump(Code.ne, 0); //jump 0 - end
		int end = Code.pc - 2;
		
//		Code.loadConst(43);
//		Code.loadConst(5);
//		Code.put(Code.print);
		
		Code.load(SemanticPass.dummy2); //index
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(SemanticPass.dummy2); //index
		
//		Code.load(SemanticPass.dummy2); //index
//		Code.loadConst(5);
//		Code.put(Code.print);
		
		Code.load(SemanticPass.dummy2); //index
		Code.load(SemanticPass.dummySet1); //array
		Code.put(Code.arraylength);			
		Code.putFalseJump(Code.eq, loopStart); //not out of bounds -> loopStart
		
		// to avoid array out of bounds
//		Code.loadConst(1);
//		Code.loadConst(1);
//		Code.putFalseJump(Code.ne, 0);
//		int endend = Code.pc - 2;
		
		//end
		Code.fixup(end);
		
		Code.load(SemanticPass.dummySet1); //array
		Code.load(SemanticPass.dummy2); //index
		Code.load(SemanticPass.dummy1); //item
		Code.put(Code.astore);
		
		// to avoid array out of bounds
		//Code.fixup(endend);
	}
	
	public void addAll() {
		//stack state: 
		//arr1
		//arr2
		
		Code.store(SemanticPass.dummySet2); //arr2 - full
		Code.store(SemanticPass.dummySet3); //arr1 - empty
		
		//load len(arr2) and index
		Code.load(SemanticPass.dummySet2); //arr2 - full
		Code.put(Code.arraylength);
		Code.store(SemanticPass.dummy3); //len(arr2)
		Code.loadConst(0);
		Code.store(SemanticPass.dummy4); //index
		
		int loopStart = Code.pc;
		
		//if item==0 -> skipAdd
		Code.load(SemanticPass.dummySet2); //arr2 - full
		Code.load(SemanticPass.dummy4); //index
		Code.put(Code.aload); //load item (arr2[index])
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		int skipAdd = Code.pc - 2;
		
		/* add */
		//load array (arr1)
		Code.load(SemanticPass.dummySet3); //arr1 - empty
		//load item (arr2[index])
		Code.load(SemanticPass.dummySet2); //arr2 - full
		Code.load(SemanticPass.dummy4); //index
		Code.put(Code.aload);
		
		add();
		/* end add */
		
		//skipAdd
		Code.fixup(skipAdd);
		
		//increment index
		Code.load(SemanticPass.dummy4); //index
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(SemanticPass.dummy4); //index
		
		
		Code.load(SemanticPass.dummy4); //index
		Code.load(SemanticPass.dummy3); //len(arr2)
		Code.putFalseJump(Code.eq, loopStart);
		
	}
	
	public void printSet() {
		//stack state: 
		//set
		
		Code.store(SemanticPass.dummySet4); //set
		
		Code.load(SemanticPass.dummySet4); //set
		Code.put(Code.arraylength);
		Code.store(SemanticPass.dummy5); //len(set)
		
		Code.loadConst(0);
		Code.store(SemanticPass.dummy6); //index
		
		//loop start
		int loopStart = Code.pc;
		
		Code.load(SemanticPass.dummySet4); //set
		Code.load(SemanticPass.dummy6); //index
		Code.put(Code.aload); //load set[index]
		Code.loadConst(0); //load 0
		Code.putFalseJump(Code.ne, 0); //jeq skip
		int skip = Code.pc - 2;
		
		Code.load(SemanticPass.dummySet4); //set
		Code.load(SemanticPass.dummy6); //index
		Code.put(Code.aload); //load set[index]
		
		Code.loadConst(5);
		Code.put(Code.print);
		
		//skip
		Code.fixup(skip);
		
		Code.load(SemanticPass.dummy6); //index
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(SemanticPass.dummy6); //index
		
		Code.load(SemanticPass.dummy6); //index
		Code.load(SemanticPass.dummy5); //len(set)
		Code.putFalseJump(Code.eq, loopStart); //jne loopStart
		
	}
	
	public void visit(DesignatorStatementSetop designatorStatementSetop) {
		//stack state: 
		//s1
		//s2
		//s3

		//store sets to dummy variables
		Code.store(SemanticPass.s3union);
		Code.store(SemanticPass.s2union);		
		Code.store(SemanticPass.s1temp);
		
//		//clear s1
//		Code.load(SemanticPass.s1);
//		Code.put(Code.arraylength);
//		Code.put(Code.newarray);
//        Code.put(1); // 1 indicates word allocation
//        Code.store(SemanticPass.s1);
		Code.load(SemanticPass.s1temp);
		Code.loadConst(0);
		Code.loadConst(0);
		Code.put(Code.astore);
        
		//addAll(s1,s2)
		Code.load(SemanticPass.s1temp);
		Code.load(SemanticPass.s2union);
		addAllUnion();
		
		Code.load(SemanticPass.s1temp);
		Code.load(SemanticPass.s3union);
		addAllUnion();
		
		
		
	}
	
	public void addAllUnion() {
		
		Code.store(SemanticPass.s3temp);
		Code.store(SemanticPass.s1temp);
		
		/* addAll* for s1 and s3 */
		
		//arr2 -> s3
		//len(arr2) -> s3Len
		//index -> indexS3
		
		//load len(arr2) and index
		Code.load(SemanticPass.s3temp); //arr2 - full
		Code.put(Code.arraylength);
		Code.store(SemanticPass.s3Len); //len(arr2)
		Code.loadConst(0);
		Code.store(SemanticPass.indexS3); //index
		
		Code.load(SemanticPass.s1temp); 
		Code.put(Code.arraylength);
		Code.store(SemanticPass.s1Len); 
		
		//loopStart
		int loopStart = Code.pc;
			
		//if item==0 -> skipAdd
		Code.load(SemanticPass.s3temp); //arr2 - full
		Code.load(SemanticPass.indexS3); //index
		Code.put(Code.aload); //load item (arr2[index])
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		int skipAdd = Code.pc - 2;
		
		/* union modif */
		Code.loadConst(0);
		Code.store(SemanticPass.indexS1); //indexS1		
		
		//unionCheckLoop
		int unionCheckLoop = Code.pc;
		
		//go through s1 and ask if item (s3[indexS3]) == s1[indexS1]
		Code.load(SemanticPass.s3temp);
		Code.load(SemanticPass.indexS3); 
		Code.put(Code.aload); 
		Code.load(SemanticPass.s1temp);
		Code.load(SemanticPass.indexS1); 
		Code.put(Code.aload); 
		Code.putFalseJump(Code.ne, 0);
		int skipAdd1 = Code.pc -2;
		
		//incr indexS1
		Code.load(SemanticPass.indexS1);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(SemanticPass.indexS1);
		
		//if indexS1 != len(S1) jump unionCheckLoop
		Code.load(SemanticPass.indexS1);
		Code.load(SemanticPass.s1Len);
		Code.putFalseJump(Code.eq, unionCheckLoop);
		
		/* end union modif */
		
		
		/* add */
		//load array (arr1)
		Code.load(SemanticPass.s1temp); //arr1 - empty
		//load item (arr2[index])
		Code.load(SemanticPass.s3temp); //arr2 - full
		Code.load(SemanticPass.indexS3); //index
		Code.put(Code.aload);
		
		add();
		/* end add */
		
		//skipAdd
		Code.fixup(skipAdd);
		
		Code.fixup(skipAdd1);
		
		//increment index
		Code.load(SemanticPass.indexS3); //index
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(SemanticPass.indexS3); //index
		
		
		Code.load(SemanticPass.indexS3); //index
		Code.load(SemanticPass.s3Len); //len(arr2)
		Code.putFalseJump(Code.eq, loopStart);
	}
	
}














