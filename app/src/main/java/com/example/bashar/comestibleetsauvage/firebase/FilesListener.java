package com.example.bashar.comestibleetsauvage.firebase;

public interface FilesListener
{
	void onFileUploaded(String url);

	void onDownloadFinished();

	void onError(String error);
}
