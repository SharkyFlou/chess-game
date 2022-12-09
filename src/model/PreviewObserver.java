package model;

public interface PreviewObserver {
    public void displayPreviewAtk(Boolean caseAtk[][]);
    public void displayPreviewMvt(Boolean caseAtk[][]);
    public void erasePreviews();
}
