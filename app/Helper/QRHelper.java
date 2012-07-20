package Helper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.Target;
import play.Play;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRHelper {

	public static String createQRImage(Target target) throws IOException,
			WriterException {

		File file = null;

		BitMatrix matrix;
		com.google.zxing.Writer writer = new QRCodeWriter();
		Map<String, Object> argumentMap = new HashMap<String, Object>();
		argumentMap.put("id", target.getId());
		// String url = Router.getFullUrl("Targets.detailJSON", argumentMap);
		String url = "http://www.yahoo.com";
		System.out.println("URL: " + url);
		matrix = writer.encode(url, com.google.zxing.BarcodeFormat.QR_CODE,
				200, 200);

		String filePath = String.format("/public/images/qr_codes/qr_%d.png",
				target.getId());

		file = Play.getFile(filePath);

		file.createNewFile();
		// file.deleteOnExit();

		MatrixToImageWriter.writeToFile(matrix, "PNG", file);

		return filePath;
	}
}
