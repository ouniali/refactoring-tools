package userinterface;

import java.io.File;

import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import JavaRefactoringAPI.JavaRefactoring;

public class RefactoringProposalExtractMethod extends RefactoringProposal{

	public RefactoringProposalExtractMethod(JavaRefactoring ref)
	{
		super(ref);
	}
	@Override
	public void apply(IDocument document) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDisplayString() {
		// TODO Auto-generated method stub
		return "Finish extract method";
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		Display display = PlatformUI.getWorkbench().getDisplay();
		Image icon = new Image(display, "C:" +File.separator+ "refactoring.png");
		return icon;
	}

}
