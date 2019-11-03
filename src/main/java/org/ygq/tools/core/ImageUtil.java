package org.ygq.tools.core;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;

public class ImageUtil {

	public static Rectangle getRectangle(BufferedImage image, float scale) {
    	int maxSize = image.getWidth() > image.getHeight() ? image.getWidth() : image.getHeight();  
    	if (maxSize < 1440) {
			return null;
		}
    	scale = scale > 1 ? 1 : scale;
    	int width = (int)(image.getWidth() * scale);
    	int height = (int)(image.getHeight() * scale);
    	int x = (image.getWidth()-width)/2;
    	int y = (image.getHeight()-height)/2;
    	Rectangle rectangle = new Rectangle(x, y, width, height);
    	return rectangle;
    }
    
    public static void main(String[] args) {
    	
    	BufferedImage read = ImgUtil.read(new File("D:\\timg 士大夫.jpg"));
    	Image cut = ImgUtil.cut(read, getRectangle(read, 0.8f));
    	ImgUtil.write(cut, new File("D:\\timg 士大夫-cut.jpg"));
    	
    	Image cut8 = ImgUtil.scale(cut, 0.8f);
    	ImgUtil.write(cut8, new File("D:\\timg 士大夫-cut-0.8.jpg"));
    	
    	Image cut6 = ImgUtil.scale(cut, 0.6f);
    	ImgUtil.write(cut6, new File("D:\\timg 士大夫-cut-0.6.jpg"));
    	
    	ByteArrayOutputStream baoscut = new ByteArrayOutputStream();
    	Img.from(cut8).setQuality(0.5f).write(baoscut);
    	FileUtil.writeBytes(baoscut.toByteArray(), new File("D:\\timg 士大夫-cut-0.8-c.jpg"));
    	
    	Image scale8 = ImgUtil.scale(read, 0.8f);
    	ImgUtil.write(scale8, new File("D:\\timg 士大夫-0.8.jpg"));
    	Image scale5 = ImgUtil.scale(read, 0.5f);
    	
    	ImgUtil.write(scale5, new File("D:\\timg 士大夫-0.5.jpg"));
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	Img.from(scale8).setQuality(0.5f).write(baos);
    	FileUtil.writeBytes(baos.toByteArray(), new File("D:\\timg 士大夫-0.8-c.jpg"));
    	
//    	ImgUtil.compress(imageFile, outFile, quality);
//    	Image scale = cn.hutool.core.img.ImgUtil.scale(read, new File("D:\\timg 士大夫2.jpg"), 0.5f);
    	
//    	ImgUtil.write(scale, new File("D:\\timg 士大夫2.jpg"));
    	
//    	ImgUtil.toImage(base64)
	}
}
