package model;

public interface PreviewObserver {
    public void displayPreviewAtk(boolean caseAtk[][]);

    public void displayPreviewMvmt(boolean caseAtk[][]);

    public void erasePreviews();
}
