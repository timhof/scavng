package models;

import javax.persistence.Entity;

@Entity
public class QRCode extends BaseModel {

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
