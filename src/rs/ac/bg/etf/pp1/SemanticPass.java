package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {

	public Struct boolType = new Struct(Struct.Bool);
	public static Struct setType = new Struct(Struct.Enum);
	
	public int printCallCount = 0;
	public int varDeclCount = 0;
	boolean returnFound = false;
	boolean errorDetected = false;
	int nVars;
	
	public Obj currentMethod = null;
	public boolean hasMain = false;
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
    
    public boolean passed(){
    	return !errorDetected;
    }
    
    public void visit(Program program){
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(program.getProgramName().obj);
    	Tab.closeScope();
    	
    	if(!hasMain) {
    		report_error("Greska: Ime " + program.getProgramName() + " nema main funkciju!", program);
    	}
    	
    	report_info("Zavrsen program " + program.getProgramName().getProgramName(), program);
    }
    
    public static Obj dummy1 = null; //add
    public static Obj dummy2 = null; //add
    public static Obj dummySet1 = null; //add
    
    public static Obj dummy3 = null; //addAll
    public static Obj dummy4 = null; //addAll
    public static Obj dummySet2 = null; //addAll
    public static Obj dummySet3 = null; //addAll
    
    public static Obj dummySet4 = null; //printSet
    public static Obj dummy5 = null; //printSet
    public static Obj dummy6 = null; //printSet
    
    public static Obj s1temp = null; //union
    public static Obj s2temp = null; //union
    public static Obj s3temp = null; //union
    public static Obj s3Len = null; //union
    public static Obj s1Len = null; //union
    public static Obj indexS1 = null; //union
    public static Obj indexS3 = null; //union
    
    public static Obj s3union = null; //union
    public static Obj s2union = null; //union
    
    public static Obj memory = null; //union
    
    public void visit(ProgramName progName){
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgramName(), Tab.noType);
    	
    	//dummy variable za add, addAll, printSet i union funkcije
    	dummy1 = Tab.insert(Obj.Var, "dummy1", Tab.intType);
    	dummy2 = Tab.insert(Obj.Var, "dummy2", Tab.intType);
    	dummySet1 = Tab.insert(Obj.Var, "dummySet1", new Struct(Struct.Array, Tab.intType));
    	
    	dummy3 = Tab.insert(Obj.Var, "dummy3", Tab.intType);
    	dummy4 = Tab.insert(Obj.Var, "dummy4", Tab.intType);
    	dummySet2 = Tab.insert(Obj.Var, "dummySet2", new Struct(Struct.Array, Tab.intType));
    	dummySet3 = Tab.insert(Obj.Var, "dummySet3", new Struct(Struct.Array, Tab.intType));
    	
    	dummySet4 = Tab.insert(Obj.Var, "dummySet4", new Struct(Struct.Array, Tab.intType));
    	dummy5 = Tab.insert(Obj.Var, "dummy5", Tab.intType);
    	dummy6 = Tab.insert(Obj.Var, "dummy6", Tab.intType);
    	
    	s1temp = Tab.insert(Obj.Var, "dummy10", new Struct(Struct.Array, Tab.intType));
    	s2temp = Tab.insert(Obj.Var, "dummy11", new Struct(Struct.Array, Tab.intType));
    	s3temp = Tab.insert(Obj.Var, "dummy12", new Struct(Struct.Array, Tab.intType));
    	s3Len = Tab.insert(Obj.Var, "dummy13", Tab.intType);
    	indexS1 = Tab.insert(Obj.Var, "dummy14", Tab.intType);
    	indexS3 = Tab.insert(Obj.Var, "dummy15", Tab.intType);
    	s1Len = Tab.insert(Obj.Var, "dummy16", Tab.intType);
    	s2union = Tab.insert(Obj.Var, "dummy17", new Struct(Struct.Array, Tab.intType));
    	s3union = Tab.insert(Obj.Var, "dummy18", new Struct(Struct.Array, Tab.intType));
    	
    	memory = Tab.insert(Obj.Var, "memory", new Struct(Struct.Array, Tab.intType));
    	
    	//dodavanje boolean
    	Tab.insert(Obj.Type, "boolean", boolType);
    	Tab.insert(Obj.Type, "set", setType);
    	
    	//dodavanje add i addAll
    	currentMethod = Tab.insert(Obj.Meth, "add", Tab.noType);
    	Tab.openScope();
    	
    	Obj varNode = Tab.insert(Obj.Var, "a", Tab.intType);
    	Obj varNode1 = Tab.insert(Obj.Var, "b", new Struct(Struct.Array, Tab.noType));
    	
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	currentMethod = null;
    	
    	currentMethod = Tab.insert(Obj.Meth, "addAll", Tab.noType);
    	Tab.openScope();
    	
    	Obj varNode2 = Tab.insert(Obj.Var, "a", new Struct(Struct.Array, Tab.intType));
    	Obj varNode3 = Tab.insert(Obj.Var, "b", new Struct(Struct.Array, Tab.noType));
    	
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	currentMethod = null;
    	//kraj dodavanje add i addAll
    	
    	
    	Tab.openScope();
    	
    	report_info("Zapocet program " + progName.getProgramName(), progName);
    }
    
    public void visit(Type type){
    	Obj typeNode = Tab.find(type.getTypeName());
    	if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
    		type.struct = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    			
    			//report_info("Pronadjen tip " + type.getTypeName(), type);
    		}else{
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
    		}
    	}
    }
    
    /* VAR DECL */
    
    public ArrayList<String> varNamesArray = new ArrayList<>();
    public ArrayList<String> isVarArray = new ArrayList<>();
    
    public void visit(VarDecl varDecl){
		//varDeclCount++;
		//report_info("Zapoceto deklarisanje var tipa "+ varDecl.getType().getTypeName(), varDecl);
		Struct type = varDecl.getType().struct;
		
		for(int i=0; i < varNamesArray.size(); i++) {
			String varName = varNamesArray.get(i);
			String isArray = isVarArray.get(i);
			
			if(isArray.equals("no")) {
				Obj varNode = Tab.insert(Obj.Var, varName, type);
			}
			
			if(isArray.equals("yes")){
				Obj varNode = Tab.insert(Obj.Var, varName, new Struct(Struct.Array, type));
			}
			
			//report_info("varName: "+ varName + " array: " + isArray, null);			
		}
		
		varDeclCount += varNamesArray.size();
		
		varNamesArray.removeAll(varNamesArray);
		isVarArray.removeAll(isVarArray);
	}
    
    public void visit(NonArrayVarDecl nonArrayVarDecl) {
    	varNamesArray.add(nonArrayVarDecl.getVarName());
    	isVarArray.add("no");
    }
    
    public void visit(ArrayVarDecl arrayVarDecl) {
    	varNamesArray.add(arrayVarDecl.getVarName());
    	isVarArray.add("yes");
    }
    
    /* END VAR DECL */
    
    
    /* CONST DECL */
    
    public ArrayList<String> constNamesArray = new ArrayList<>();
    public ArrayList<Struct> constValuesArray = new ArrayList<>();
    //codegen
    public ArrayList<Integer> setAdrNumberArray = new ArrayList<>();
    
    public void visit(ConstDecl constDecl) {
    	//report_info("Usao u constDecl ", constDecl);
    	
    	Struct type = constDecl.getType().struct;
    	constNamesArray.add(constDecl.getConstName());
    	
    	
    	Collections.reverse(constNamesArray);
    	
    	for(int i=0; i<constNamesArray.size(); i++) {
    		//report_info("constName: "+ constNamesArray.get(i) + " " , constDecl);
    		
    		if(!type.equals(constValuesArray.get(i))) {
    			report_error("Nekompatibilan tip dodele const: "+ constNamesArray.get(i) , constDecl);
    		} else {
    			Obj varNode = Tab.insert(Obj.Con, constNamesArray.get(i), type);
    			//constDecl.obj = varNode;
    			//codegen
    			if(setAdrNumberArray.size()-1 >= i) {
    				varNode.setAdr(setAdrNumberArray.get(i));
    			}  			
    		}
    	}
    	
    	constNamesArray.removeAll(constNamesArray);
    	constValuesArray.removeAll(constValuesArray);
    	setAdrNumberArray.removeAll(setAdrNumberArray);
    }
    
    public void visit(MoreConstsList1 moreConstsList1) {
    	//report_info("Usao u moreConstsList1 " + moreConstsList1.getConstName(), moreConstsList1);
    	
    	constNamesArray.add(moreConstsList1.getConstName());
    }
    
    public void visit(ConstTypeNumber constTypeNumber) {
    	//report_info("Usao u constTypeNumber ", constTypeNumber);
    	
    	constValuesArray.add(Tab.intType);
    	
    	//codegen
    	setAdrNumberArray.add(constTypeNumber.getN1());
    }
    
    public void visit(ConstTypeChar constTypeChar) {
    	//report_info("Usao u constTypeChar ", constTypeChar);
    	
    	constValuesArray.add(Tab.charType);
    }
    
    public void visit(ConstTypeBool constTypeBool) {
    	//report_info("Usao u constTypeBool ", constTypeBool);
    	
    	//nmp
    	constValuesArray.add(boolType);
    }
	
    /* END CONST DECL */
    
    
    /* METHOD DECL */
    
    public void visit(VoidMethodSignature voidMethodSignature) {
    	//report_info("Usao u voidMethodSignature", voidMethodSignature);
    	currentMethod = Tab.insert(Obj.Meth, voidMethodSignature.getMethodName(), Tab.noType);
    	
    	if(voidMethodSignature.getMethodName().equals("main")) {
    		hasMain = true;
    	}
    	
    	Tab.openScope();
    	
    	//report_info("Obradjuje se funkcija " + voidMethodSignature.getMethodName(), voidMethodSignature);
    }
    
    public void visit(MethodDecl methodDecl){
    	//report_info("Usao u methodDecl", methodDecl);

    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	
    	currentMethod = null;
    }
    
    /* END METHOD DECL */
    
    
    /* STATEMENT */
    public void visit(ReadStatement readStatement) {
    	Struct type = readStatement.getDesignator().obj.getType();
    	
    	if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
    		report_error("Greska: Designator mora biti tipa int, char ili boolean!", readStatement);
    	}
    }
    
    public void visit(PrintStatement printStatement) {
    	Struct type = printStatement.getExpr().struct;
    	
    	if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType) && !type.equals(setType)) {
    		report_error("Greska: Izraz mora biti tipa int, char, boolean ili set!", printStatement);
    	}
    	
    	printCallCount++;
    }
    
    /* END STATEMENT */
    
    
    /* IF STATEMENT */
    public void visit(CondFactWithoutRelop condFactWithoutRelop) {
    	condFactWithoutRelop.struct = condFactWithoutRelop.getExpr().struct;
    	
    	if(!condFactWithoutRelop.struct.equals(boolType)) {
    		report_error("Greska: Izraz mora biti tipa boolean!", condFactWithoutRelop);
    	}
    }
    
    public void visit(CondFactWithRelop condFactWitRelop) {
    	Struct exprType1 = condFactWitRelop.getExpr().struct;
    	Struct exprType2 = condFactWitRelop.getExpr1().struct;
    	
    	if(!exprType1.compatibleWith(exprType2)) {
    		report_error("Greska: Izrazi nisu kompatibilni!", condFactWitRelop);
    	}
    }
    
    /* END IF STATEMENT */
    
    
    /* DESIGNATOR STATEMENT */
    public boolean isDesignatorArray = false;
    public void visit(Designator1 designator){
    	//report_info("Usao u designator " + designator.getDesignatorName(), designator);
    	
    	Obj designatorNode = Tab.find(designator.getDesignatorName());
    	if(designatorNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + designator.getDesignatorName() + " u tabeli simbola! ", null);
    		//designator.struct = Tab.noType;
    		designator.obj = designatorNode;
    	}
    	else{
    		designator.obj = designatorNode;
    	}
    	
//    	if(isDesignatorArray) {
//    		if(designator.obj.getKind() != Struct.Array) {
//    			report_error("Greska: Designator " + designator.getDesignatorName() + " mora biti niz!", designator);
//    		}
//    		isDesignatorArray = false;
//    	}
    	
    	if(isDesignatorArray) {
    		Struct structIntArray = new Struct(Struct.Array, Tab.intType);
    		Struct structCharArray = new Struct(Struct.Array, Tab.charType);
    		Struct structBoolArray = new Struct(Struct.Array, boolType);
    		
    		if(!designator.obj.getType().equals(structIntArray) && !designator.obj.getType().equals(structCharArray) && !designator.obj.getType().equals(structBoolArray)) {
    			report_error("Greska: Designator " + designator.getDesignatorName() + " mora biti niz!", designator);
    		}
    		isDesignatorArray = false;
    	}
    }
    
    public void visit(DesignatorElemOfArray designator){
    	//report_info("Usao u designator " + designator.getDesignatorName(), designator);
    	
    	if(!designator.getExpr().struct.equals(Tab.intType) && !designator.getExpr().struct.equals(new Struct(Struct.Array, Tab.intType))) {
    		report_error("Greska: Izraz u [] mora biti tipa int!", designator);	
    	}
    	
    	Obj designatorNode = Tab.find(designator.getDesignatorName());
    	if(designatorNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + designator.getDesignatorName() + " u tabeli simbola! ", null);
    		//designator.struct = Tab.noType;
    		designator.obj = designatorNode;
    	}
    	else{
    		designator.obj = designatorNode;
    	}
    	
//    	if(isDesignatorArray) {
//    		if(designator.obj.getKind() != Struct.Array) {
//    			report_error("Greska: Designator " + designator.getDesignatorName() + " mora biti niz!", designator);
//    		}
//    		isDesignatorArray = false;
//    	}
    	
    	if(isDesignatorArray) {
    		Struct structIntArray = new Struct(Struct.Array, Tab.intType);
    		Struct structCharArray = new Struct(Struct.Array, Tab.charType);
    		Struct structBoolArray = new Struct(Struct.Array, boolType);
    		
    		if(!designator.obj.getType().equals(structIntArray) && !designator.obj.getType().equals(structCharArray) && !designator.obj.getType().equals(structBoolArray)) {
    			report_error("Greska: Designator " + designator.getDesignatorName() + " mora biti niz!", designator);
    		}
    		isDesignatorArray = false;
    	}
    }
    
    // designator [ expr ] 
    public void visit(DesignatorExpr designatorExpr) {
    	//report_info("Usao u designatorExpr", designatorExpr);
    	
    	if(!designatorExpr.getExpr().struct.equals(Tab.intType) && !designatorExpr.getExpr().struct.equals(new Struct(Struct.Array, Tab.intType))) {
    		report_error("Greska: Izraz u [] mora biti tipa int!", designatorExpr);	
    	}
    	
    	isDesignatorArray = true;
    }
    
    public void visit(DesignatorStatementPlusPlus designatorStatementPlusPlus) {
    	if(!designatorStatementPlusPlus.getDesignator().obj.getType().equals(Tab.intType)) {
    		report_error("Greska: Ime " + ((Designator1) designatorStatementPlusPlus.getDesignator()).getDesignatorName() + " mora biti int!", designatorStatementPlusPlus);
    	}
    }
    
    public void visit(DesignatorStatementMinusMinus designatorStatementMinusMinus) {
    	String designatorName = "";
    	
    	if(designatorStatementMinusMinus.getDesignator() instanceof Designator1) {
    		designatorName = ((Designator1) designatorStatementMinusMinus.getDesignator()).getDesignatorName();
    	} else {
    		designatorName = ((DesignatorElemOfArray) designatorStatementMinusMinus.getDesignator()).getDesignatorName();
    	}
    	
    	if(!designatorStatementMinusMinus.getDesignator().obj.getType().equals(Tab.intType)) {
    		report_error("Greska: Ime " + designatorName + " mora biti int!", designatorStatementMinusMinus);
    	}
    }
    
    public void visit(DesignatorStatementAssignopExpression designatorStatementAssignopExpression) {
    	Struct designator = designatorStatementAssignopExpression.getDesignator().obj.getType();
    	Struct expr = designatorStatementAssignopExpression.getExpr().struct;
    	
    	//debug
//    	if(expr.equals(new Struct(Struct.Array, Tab.intType))){
//			report_error("designator = expr, expr intArray: true", designatorStatementAssignopExpression);
//		}
//    	
//    	if(designator.equals(new Struct(Struct.Array, Tab.intType))){
//			report_error("designator = expr, designator intArray: true", designatorStatementAssignopExpression);
//		}
    	//end debug
    	
    	if(expr.getKind() == Struct.Array) {
    		String designatorName = "";
			if(designatorStatementAssignopExpression.getDesignator() instanceof Designator1) {
				designatorName = ((Designator1) designatorStatementAssignopExpression.getDesignator()).getDesignatorName();
			} else {			
				designatorName = ((DesignatorElemOfArray) designatorStatementAssignopExpression.getDesignator()).getDesignatorName();
			}
    		
    		if(designator.equals(Tab.intType)) {
    			if(!expr.equals(new Struct(Struct.Array, Tab.intType))){
    				report_error("Greska: designator " + designatorName 
    						+ " i izraz nisu kompatibilni!", designatorStatementAssignopExpression);
    			}
    		}
    		
    		if(designator.equals(Tab.charType)) {
    			if(!expr.equals(new Struct(Struct.Array, Tab.charType))){
    				report_error("Greska: designator " + designatorName 
    						+ " i izraz nisu kompatibilni!", designatorStatementAssignopExpression);
    			}
    		}
    		
    		if(designator.equals(boolType)) {
    			if(!expr.equals(new Struct(Struct.Array, boolType))){
    				report_error("Greska: designator " + designatorName 
    						+ " i izraz nisu kompatibilni!", designatorStatementAssignopExpression);
    			}
    		}
    	} else {
    		if(designator.getKind() == Struct.Array) {
    			String designatorName = "";
    			if(designatorStatementAssignopExpression.getDesignator() instanceof Designator1) {
    				designatorName = ((Designator1) designatorStatementAssignopExpression.getDesignator()).getDesignatorName();
    			} else {			
    				designatorName = ((DesignatorElemOfArray) designatorStatementAssignopExpression.getDesignator()).getDesignatorName();
    			}
    			
        		if(designator.equals(new Struct(Struct.Array, Tab.intType))) {
        			if(!expr.equals(Tab.intType)){
        				report_error("Greska: designator " + designatorName
        						+ " i izraz nisu kompatibilni!", designatorStatementAssignopExpression);
        			}
        		}
        		
        		if(designator.equals(new Struct(Struct.Array, Tab.charType))) {
        			if(!expr.equals(Tab.charType)){
        				report_error("Greska: designator " + designatorName 
        						+ " i izraz nisu kompatibilni!", designatorStatementAssignopExpression);
        			}
        		}
        		
        		if(designator.equals(new Struct(Struct.Array, boolType))) {
        			if(!expr.equals(boolType)){
        				report_error("Greska: designator " + designatorName 
        						+ " i izraz nisu kompatibilni!", designatorStatementAssignopExpression);
        			}
        		}
        	} 
    	}
    	
    	if(!(expr.getKind() == Struct.Array || designator.getKind() == Struct.Array)) {
    		if(!designator.compatibleWith(expr)) {
        		report_error("Greska: designator i expression nisu kompatibilni!", designatorStatementAssignopExpression);
        		//report_error("type int[]: " + expr.equals(new Struct(Struct.Array, Tab.intType)), designatorStatementAssignopExpression);
        		//report_error("type int: " + expr.equals(Tab.intType), designatorStatementAssignopExpression);
        	}
    	}
    	
    	
    	
    	
//    	else {
//    		if(!designator.compatibleWith(expr)) {
//        		report_error("Greska: designator i expression nisu kompatibilni!", designatorStatementAssignopExpression);
//        		//report_error("type int[]: " + expr.equals(new Struct(Struct.Array, Tab.intType)), designatorStatementAssignopExpression);
//        		//report_error("type int: " + expr.equals(Tab.intType), designatorStatementAssignopExpression);
//        	}
//    		
//    	}
    }
    
    public void visit(DesignatorStatementSetop designatorStatementSetop) {
    	Struct designator = designatorStatementSetop.getDesignator().obj.getType();
    	Struct designator1 = designatorStatementSetop.getDesignator1().obj.getType();
    	Struct designator2 = designatorStatementSetop.getDesignator2().obj.getType();
    	
    	if(!designator.equals(setType) || !designator1.equals(setType) || !designator2.equals(setType)) {
    		report_error("Greska: Svaki designator mora da bude skup!", designatorStatementSetop);
    	}
    	
//    	if(designator.getKind() != Struct.Array || designator1.getKind() != Struct.Array || designator2.getKind() != Struct.Array) {
//    		report_error("Greska: Svaki designator mora da bude skup!", designatorStatementSetop);
//    	}
    }
    
    /* END DESIGNATOR STATEMENT */
    
    
    /* EXPR */
    public ArrayList<Struct> addopTermsArray = new ArrayList<>();
    
    public void visit(TermExpression termExpression) {
    	//report_info("Usao u termExpression", termExpression);
    	
    	termExpression.struct = termExpression.getTerm().struct;
    	
    	if(termExpression.struct.getKind() == Struct.Array) {
    		if(termExpression.struct.equals(new Struct(Struct.Array, Tab.intType))) {
    			termExpression.struct = Tab.intType;
    		}
    		
    		if(termExpression.struct.equals(new Struct(Struct.Array, Tab.charType))) {
    			termExpression.struct = Tab.charType;
    		}
    		
    		if(termExpression.struct.equals(new Struct(Struct.Array, boolType))) {
    			termExpression.struct = boolType;
    		}
    	}
    
    	boolean error = false;
    	for(int i = 0; i < addopTermsArray.size(); i++) {
    		if(!termExpression.struct.equals(addopTermsArray.get(i))) {
    			error = true;
    		}
    	}
    	
    	if(error) {
    		report_error("Greska: Parametri izraza moraju biti tipa int!", termExpression);
    	}
    	
    	addopTermsArray.removeAll(addopTermsArray);
    	//report_info("termExpression typeInt: " + termExpression.struct.equals(Tab.intType), termExpression);
    }
    
    public void visit(MinusTermExpression minusTermExpression) {
    	//report_info("Usao u minusTermExpression", minusTermExpression);
    	
    	minusTermExpression.struct = minusTermExpression.getTerm().struct;
    	
    	if(minusTermExpression.struct.getKind() == Struct.Array) {
    		if(minusTermExpression.struct.equals(new Struct(Struct.Array, Tab.intType))) {
    			minusTermExpression.struct = Tab.intType;
    		}
    		
    		if(minusTermExpression.struct.equals(new Struct(Struct.Array, Tab.charType))) {
    			minusTermExpression.struct = Tab.charType;
    		}
    		
    		if(minusTermExpression.struct.equals(new Struct(Struct.Array, boolType))) {
    			minusTermExpression.struct = boolType;
    		}
    	}
    	
    	if(!minusTermExpression.struct.equals(Tab.intType)) {
    		report_error("Greska: Izraz mora biti tipa int!", minusTermExpression);
    	}
    	
    	boolean error = false;
    	for(int i = 0; i < addopTermsArray.size(); i++) {
    		if(!minusTermExpression.struct.equals(addopTermsArray.get(i))) {
    			error = true;
    		}
    	}
    	
    	if(error) {
    		report_error("Greska: Parametri izraza moraju biti tipa int!", minusTermExpression);
    	}
    	
    	addopTermsArray.removeAll(addopTermsArray);
    }
    
    public void visit(AddopTermList1 addopTermList1) {
    	//report_info("Usao u addopTermList1", addopTermList1);
    	
    	addopTermsArray.add(addopTermList1.getTerm().struct);
    }
    
    public boolean factorMulopFactor = false;
    public void visit(Term term) {
    	//report_info("Usao u term", term);
    	
    	term.struct = term.getFactor().struct;
    	
    	if(factorMulopFactor) {
    		if(!(term.struct.equals(Tab.intType) || term.struct.equals(new Struct(Struct.Array, Tab.intType)))) {
        		report_error("Greska: Parametri izraza moraju biti tipa int!", term);
        	}
    		
    		//debug
//    		if(!(term.struct.equals(Tab.intType))) {
//        		report_error("Greska factmulfact: Parametri izraza moraju biti tipa int!", term);
//        	}
    		
    		factorMulopFactor = false;
    	}
    }
    
    public void visit(MulopFactorList1 mulopFactorList1) {
    	Struct type = mulopFactorList1.getFactor().struct;
    	
    	if(!(type.equals(Tab.intType) || type.equals(new Struct(Struct.Array, Tab.intType)))) {
    		report_error("Greska: Parametri izraza moraju biti tipa int!", mulopFactorList1);
    	}
    	
//    	//debug
//    	if(!type.equals(Tab.intType)) {
//    		report_error("Greska mulopFactorList1: Parametri izraza moraju biti tipa int!", mulopFactorList1);
//    	}
    	
    	factorMulopFactor = true;
    }
    
    public boolean newTypeIsArray = false;
    
    public void visit(ExprOrActPars1 exprOrActPars1) {
    	if(!exprOrActPars1.getExpr().struct.equals(Tab.intType)) {
    		report_error("Greska: Izraz mora biti tipa int!", exprOrActPars1);
    	}
    	
    	newTypeIsArray = true;
    }
    
    public void visit(NewTypeFactor newTypeFactor) {
    	//report_info("Usao u newTypeFactor: ", newTypeFactor);
    	
    	Struct type;
    	if(newTypeIsArray) {
    		type = new Struct(Struct.Array, newTypeFactor.getType().struct);
    		newTypeIsArray = false;
    	} else {
    		type = newTypeFactor.getType().struct;
    	}  
    	
    	//report_info("Type equals setType: " + newTypeFactor.getType().struct.equals(setType), newTypeFactor);
    	
//    	//debug
//    	type = newTypeFactor.getType().struct;
    	
    	newTypeFactor.struct = type;
    	
    }

    public void visit(NumFactor numFactor) {
    	//report_info("Usao u numFactor: " + numFactor.getValue(), numFactor);
    	
    	numFactor.struct = Tab.intType;
    }
    
    public void visit(CharFactor charFactor) {
    	//report_info("Usao u charFactor: " + charFactor.getValue(), charFactor);
    	
    	charFactor.struct = Tab.charType;
    	
    }
    
    public void visit(BoolFactor boolFactor) {
    	//report_info("Usao u boolFactor: " + boolFactor.getValue(), boolFactor);
    	
    	boolFactor.struct = boolType;
    }
    
    public void visit(ExprFactor exprFactor) {
    	//report_info("Usao u exprFactor: ", exprFactor);
    	
    	exprFactor.struct = exprFactor.getExpr().struct;
    }
    
    public void visit(DesignatorFactor designatorFactor) {
    	//report_info("Usao u designatorFactor: ", designatorFactor);
    	
    	if(designatorFactor.getDesignator() instanceof Designator1) {
    		designatorFactor.struct = ((Designator1) designatorFactor.getDesignator()).obj.getType();
    		
    	} else {
    		designatorFactor.struct = ((DesignatorElemOfArray) designatorFactor.getDesignator()).obj.getType();
    	}
    	
    	
    }
    
    /* END EXPR */
    
}







