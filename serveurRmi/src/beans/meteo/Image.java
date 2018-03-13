package beans.meteo;

import java.io.Serializable;

public class Image implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idImage;
	private byte[] image;
	
	public int getIdImage() {
		return idImage;
	}
	
	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	

}
