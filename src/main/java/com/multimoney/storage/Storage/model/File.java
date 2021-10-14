package com.multimoney.storage.Storage.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
  
    private String name;
  
    private String type;
  
    @Lob
    private byte[] data;
  
    public File() {
    }
  
    public File(String name, String type, byte[] data) {
      this.name = name;
      this.type = type;
      this.data = data;
    }
  
    public String getId() {
      return id;
    }
  
    public String getName() {
      return name;
    }
  
    public void setName(String name) {
      this.name = name;
    }
  
    public String getType() {
      return type;
    }
  
    public void setType(String type) {
      this.type = type;
    }
  
    public byte[] getData() {
      return data;
    }
  
    public void setData(byte[] data) {
      this.data = data;
    }

    public byte[] scale(int width, int height) throws Exception {

      if (width == 0 || height == 0)
        return data;
  
      ByteArrayInputStream in = new ByteArrayInputStream(data);
  
      try {
        BufferedImage img = ImageIO.read(in);
  
        java.awt.Image scaledImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage imgBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        imgBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
  
        ImageIO.write(imgBuff, "jpg", buffer);
        setData(buffer.toByteArray());
        return buffer.toByteArray();
  
      } catch (Exception e) {
        throw new Exception("IOException in scale");
      }
    }
    
}
