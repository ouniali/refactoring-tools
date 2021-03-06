package userinterface;

import java.io.File;

import org.eclipse.jdt.ui.text.java.IJavaCompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import flexiblerefactoring.BeneFactor;

import JavaRefactoringAPI.JavaUndoRefactoring;

abstract public class UndoRefactoringProposal implements IJavaCompletionProposal {

	JavaUndoRefactoring undo;
	public UndoRefactoringProposal(JavaUndoRefactoring u)
	{
		undo = u;
	}
	@Override
	public void apply(IDocument document) {
		new Thread(undo).start();
	}

	@Override
	public Point getSelection(IDocument document) {return null;}

	@Override
	public String getAdditionalProposalInfo() {	return null;}

	@Override
	abstract public String getDisplayString();

	@Override
	public Image getImage() {
		String path = BeneFactor.getIconPath(getImageFileName());
		if(new File(path).exists())
		{
			Display display = PlatformUI.getWorkbench().getDisplay();
			Image icon = new Image(display, path);
			return icon;
		}
		else
			return null;
	}

	@Override
	public IContextInformation getContextInformation() {
		return null;
	}

	@Override
	public int getRelevance() {	return Integer.MAX_VALUE;}
	
	abstract protected String getImageFileName();

}
