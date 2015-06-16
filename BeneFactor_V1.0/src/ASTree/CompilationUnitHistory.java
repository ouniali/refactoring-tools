package ASTree;

import java.util.*;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.*;

import userinterface.RefactoringMarker;

import compilation.RefactoringChances;

import ExtractMethod.ASTExtractMethodChangeInformation;
import ExtractMethod.ExtractMethod;
import JavaRefactoringAPI.JavaRefactoring;
import Rename.ASTNameChangeInformation;
import Rename.NameChange;
import Rename.NameChangeCountHistory;

public class CompilationUnitHistory {
	
	String ProjectName;
	String PackageName;
	String UnitName;
	IJavaProject Project;
	ICompilationUnit unit;
	ArrayList<CompilationUnitHistoryRecord> Records;
	
	protected CompilationUnitHistory(IJavaProject proj, ICompilationUnit u, String pro, String pac, String un)
	{
		unit = u;
		Project = proj;
		ProjectName = pro;
		PackageName = pac;
		UnitName = un;
		Records = new ArrayList<CompilationUnitHistoryRecord>();
	}
	
	
	protected boolean addAST(CompilationUnit tree) throws Exception
	{
		if(Records.size()>0)
		{
			CompilationUnit lastUnit = Records.get(Records.size()-1).getASTree();
			if(tree.subtreeMatch(new ASTMatcher(), lastUnit))
				return false;
		}
		
		Records.add(new CompilationUnitHistoryRecord(Project, unit ,ProjectName, PackageName, UnitName,System.currentTimeMillis()));
		
		detectRefactoringOpportunity(Records, unit);
		
		return true;
	}
	

	
	protected ASTChangeInformation getMostRecentASTGeneralChange()
	{
		CompilationUnitHistoryRecord newRecord = Records.get(Records.size()-1);
		CompilationUnitHistoryRecord oldRecord = null;
		
		if(Records.size()<=1)
			return null;
		oldRecord = Records.get(Records.size()-2);
		ASTChangeInformation change = ASTChangeInformationGenerator.getGeneralASTChangeInformation(oldRecord, newRecord);
		return change;
	}
	protected String getCompilationUnitName()
	{
		return UnitName;
	}
	
	protected String getPackageName()
	{
		return PackageName;
	}
	
	static private void detectRefactoringOpportunity(ArrayList<CompilationUnitHistoryRecord> records, ICompilationUnit unit) throws Exception
	{
		JavaRefactoring refactoring;
		if(NameChange.LookingBackForDetectingRenameChange(records))
		{
			ASTNameChangeInformation infor = NameChange.detectedNameChanges.get(NameChange.detectedNameChanges.size()-1);
			refactoring = infor.getRenameRefactoring(unit);
			if(refactoring != null)
			{
				RefactoringChances.addNewRefactoringChance(refactoring);
				System.out.println("Rename detected.");	
			}
		}
		if(ExtractMethod.LookingBackForDetectingExtractMethodChange(records))
		{
			ASTExtractMethodChangeInformation infor =  ExtractMethod.detectedExtractMethodChanges.get(ExtractMethod.detectedExtractMethodChanges.size()-1);
			refactoring = infor.getJavaExtractMethodRefactoring(unit);
			if(refactoring != null)
			{
				RefactoringChances.addNewRefactoringChance(refactoring);
				System.out.println("Extract method detected.");
			}
		}		
	}
	

	
}
