package model;

public interface PreviewObserver {
    public void displayPreviewAtk(Boolean caseAtk[][]);
    public void displayPreviewMvmt(Boolean caseAtk[][]);
    public void erasePreviews();
}
