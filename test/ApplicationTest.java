import java.io.IOException;

import models.Target;

import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;
import Helper.QRHelper;

import com.google.zxing.WriterException;

public class ApplicationTest extends FunctionalTest {

	@Test
	public void testThatIndexPageWorks() {
		Response response = GET("/");
		assertIsOk(response);
		assertContentType("text/html", response);
		assertCharset(play.Play.defaultWebEncoding, response);
	}

	@Test
	public void testQRCode() throws IOException, WriterException {

		String path = QRHelper.createQRImage(new Target());
		System.out.println("WROTE FILE");
	}
}