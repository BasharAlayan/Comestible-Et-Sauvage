package com.example.bashar.comestibleetsauvage.firebase;

public interface FilesListener
{
	void onFileUploaded(String url, String localPath);

	void onDownloadFinished();

	void onError(String error);
}
