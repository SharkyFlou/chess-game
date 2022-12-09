package model;

public interface PreviewObserver {
    public void displayPreviewAtk(boolean caseAtk[][]);

    public void displayPreviewMvt(boolean caseAtk[][]);

    public void erasePreviews();
}
